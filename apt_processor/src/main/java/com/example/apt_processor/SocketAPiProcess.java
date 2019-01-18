package com.example.apt_processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.xian_zhong.apt_annotation.SocketApi;
import com.xian_zhong.apt_annotation.SocketObjParam;
import com.xian_zhong.apt_annotation.SocketTimeParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author xian_zhong  admin
 * @version 1.0
 * @title com.example.apt_processor  SocketBase
 * @Des SocketAPiProcess
 */
@AutoService(Processor.class)
public class SocketAPiProcess extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        set.add(SocketApi.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(SocketApi.class);
        List<String> list = new ArrayList<>();
        List<ExecutableElement> elist = new ArrayList<>();
        TypeElement typeElement = null;
        String packageName = "";
        //api 对象元素
        for (Element e : elementsAnnotatedWith){
            ExecutableElement executableElement = (ExecutableElement)e;
            typeElement = (TypeElement)e.getEnclosingElement();
            packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
            SocketApi annotation = executableElement.getAnnotation(SocketApi.class);
            String value = annotation.value();
            list.add(value);
            elist.add(executableElement);
        }
        TypeSpec.Builder builder = null;
        if (typeElement != null){
            builder = generatedJavaFile(typeElement);
        }
        if (builder == null){
            return true;
        }
        for (int i = 0 ; i < list.size() ; i++){
            builder.addMethod(generatedMethod(packageName+".bean",list.get(i),elist.get(i)));
        }
        JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private TypeSpec.Builder generatedJavaFile(TypeElement name){
        return TypeSpec.classBuilder(name.getSimpleName().toString() + "Impl")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.bestGuess(name.getQualifiedName().toString()));
    }

    private TypeSpec generatedJavaData(String name,Map<String,TypeName> map){
        return TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .addFields(generatedField(map))
                .addMethod(generatedCus(map))
                .build();
    }

    private MethodSpec generatedCus(Map<String,TypeName> map){
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameters(generatedParam(map));
        for (String s : map.keySet()){
            builder.addStatement(generatedStatement(),s,s);
        }
        return builder.build();
    }

    private String generatedStatement(){
        return "this.$L = $L";
    }

    private List<ParameterSpec> generatedParam(Map<String,TypeName> map){
        List<ParameterSpec> list = new ArrayList<>();
        for (String type : map.keySet()){
            ParameterSpec build = ParameterSpec.builder(map.get(type), type).build();
            list.add(build);
        }
        return list;
    }

    private List<FieldSpec> generatedField(Map<String,TypeName> map){
        List<FieldSpec> fieldSpecList = new ArrayList<>();
        for (String s : map.keySet()) {
            FieldSpec build = FieldSpec.builder(map.get(s), s, Modifier.PUBLIC).build();
            fieldSpecList.add(build);
        }
        return fieldSpecList;
    }

    private MethodSpec generatedMethod(String packageName,String s , ExecutableElement element){
        Boolean isRxJava = true;
        String simpleName = element.getSimpleName().toString();
//        List<? extends TypeParameterElement> typeParameters = element.getTypeParameters();
        List<? extends VariableElement> parameters = element.getParameters();

        TypeMirror returnType = element.getReturnType();
        MethodSpec.Builder builder = MethodSpec.overriding(element);
        if (returnType.toString().endsWith("Callable")){
            isRxJava = false;
//            ClassName className = ClassName.bestGuess(returnType.toString());
//            builder.returns(className);
        }else {
//            TypeName typeName = ParameterizedTypeName.get(returnType);
                /*Class<?> aClass = Class.forName(className.simpleName());
                Type[] actualTypeArguments = ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments();
                ParameterizedTypeName name = ParameterizedTypeName.get(aClass,actualTypeArguments);*/
//            builder.returns(typeName);
        }
//                .returns();
        String time = "";
        VariableElement element1 = null;
        Map<String,TypeName> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (int i = 0;i<parameters.size();i++){
            SocketObjParam annotation = parameters.get(i).getAnnotation(SocketObjParam.class);
            if (annotation != null){  //生成对象
                String value = annotation.value();
                ClassName className = ClassName.bestGuess(parameters.get(i).asType().toString());
                map.put(value,className);
                list.add(parameters.get(i).getSimpleName().toString());
            }else {
                SocketTimeParam annotation1 = parameters.get(i).getAnnotation(SocketTimeParam.class);
                if (annotation1 != null){
                    time = parameters.get(i).getSimpleName().toString();
                }else {
                    element1 = parameters.get(i);
                }
            }
//            builder.addParameter(ClassName.bestGuess(parameters.get(i).asType().toString()), parameters.get(i).getSimpleName().toString());
        }
        if (!map.isEmpty() && !list.isEmpty() && map.size() == list.size()){
            JavaFile build = JavaFile.builder(packageName, generatedJavaData(toUpperCaseFirstOne(simpleName + "Data"), map)).build();
            try {
                build.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
            generatedBeanCode(builder,time,isRxJava,s,list,packageName+"."+toUpperCaseFirstOne(simpleName + "Data"));
            return builder.build();
        }
        generatedCode(s, isRxJava, element1, builder, time);
        return builder.build();
    }

    private void generatedBeanCode(MethodSpec.Builder builder,String time,Boolean isRxJava,String s,List<String> parameters,String dataName){
        ClassName className;
        if (!isRxJava){
            className = ClassName.bestGuess("com.zgkjd.basesocket.client.KJDClient");
        }else {
            className = ClassName.bestGuess("com.zgkjd.basesocket.client.RxKJDClient");
        }
        StringBuilder pa = new StringBuilder();
        for (int i = parameters.size() - 1; i >= 0; i--) {
            pa.append(parameters.get(i) + ",");
        }
        pa.delete(pa.length() - 1,pa.length());
        ClassName className1 = ClassName.bestGuess(dataName);
        builder.addStatement("$T bean = new $T($L)",className1,className1,pa.toString())
                .addStatement("return $T.INSTANCE.requestApi(\"$L\",$L,$L)",className,s,"bean",time.isEmpty() ? 0 : time);
    }



    private void generatedCode(String s, Boolean isRxJava, VariableElement parameters, MethodSpec.Builder builder,String time) {
        ClassName className;
        if (!isRxJava){
            className = ClassName.bestGuess("com.zgkjd.basesocket.client.KJDClient");
        }else {
            className = ClassName.bestGuess("com.zgkjd.basesocket.client.RxKJDClient");
        }
        builder.addStatement("return $T.INSTANCE.requestApi(\"$L\",$L,$L)",className,s,parameters.getSimpleName().toString(),time.isEmpty() ? 0 : time);
    }

    private static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


}
