package com.zgkjd.basesocket.socket

import android.util.Log
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.codec.MessageToByteEncoder

/**
 * Created by admin on 2019/1/9.
 */
object SocketClient {

    var nioEventLoopGroup : NioEventLoopGroup? = null
    var ch : Channel? = null
    var mHost = ""
    var mPort = 0
    var bootstrap : Bootstrap? = null

    var isStop = true
    var lineEnds = "\n"   //发送接收数据的结尾限制符
    var mListener : ISocketClient? = null

    fun init(){
        nioEventLoopGroup = NioEventLoopGroup()
        bootstrap = Bootstrap()
        bootstrap?.group(nioEventLoopGroup)
                ?.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                ?.channel(NioSocketChannel::class.java)
                ?.handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch?.pipeline()?.addLast(object : MessageToByteEncoder<String>() {
                            override fun encode(ctx: ChannelHandlerContext?, msg: String?, out: ByteBuf?) {
                                out?.writeBytes(msg?.toByteArray())
                            }
                        })
                                ?.addLast(LineBasedFrameDecoder(1024_000))
                                ?.addLast(HandlerAdapter())
                    }
                })
    }

    fun connect(host : String,port:Int){
        if (!isStop) return
        mHost = host
        mPort = port
        if (nioEventLoopGroup == null){
            init()
        }
        bootstrap?.connect(mHost, mPort)?.addListener {
            if (it.isSuccess){
                ch = (it as ChannelFuture).channel()
                isStop = false
                mListener?.connectSuccess()
            }
        }?.sync()

    }

    fun stopConnect(){
        if (isStop) return
        isStop = true
        if (ch != null && ch!!.isOpen){
            ch?.close()
            ch = null
        }
        if (nioEventLoopGroup != null){
            nioEventLoopGroup?.shutdownGracefully()
            nioEventLoopGroup = null
        }
    }

    fun writeData(s : String){
        if (ch != null && ch!!.isOpen){
            Log.i("socket_test",s)
            ch?.writeAndFlush("${s + lineEnds}")?.addListener {
                if (!it.isSuccess){
                    mListener?.writeFail(s)
                }
            }
        }
    }

    class HandlerAdapter : ChannelInboundHandlerAdapter(){
        override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
            val str = if (msg is ByteBuf) bytebufToString(msg) else ""
            mListener?.resultMsg(str)
        }

        override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
            cause?.printStackTrace()
            ctx?.close()
        }

        fun bytebufToString(buf : ByteBuf) : String{
            var str = ""
            if(buf.hasArray()) { // 处理堆缓冲区
                str = String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes())
            } else { // 处理直接缓冲区以及复合缓冲区
                val bytes = ByteArray(buf.readableBytes())
                buf.getBytes(buf.readerIndex(), bytes)
                str =String(bytes, 0, buf.readableBytes())
            }
            Log.i("socket_test_text","read::"+str)
            return str
        }
    }
}