package com.github.zxh0.pbg.gen;

import java.util.HashSet;
import java.util.Set;

public class ProtoGenTool {

    private String protoDir;
    private String javaDir;
    private String cppDir;
    private String protoc;
    private Set<Class<?>> protos = new HashSet<>();

    public ProtoGenTool setProtoDir(String protoDir) {
        this.protoDir = protoDir;
        return this;
    }

    public ProtoGenTool setJavaDir(String javaDir) {
        this.javaDir = javaDir;
        return this;
    }

    public ProtoGenTool setCppDir(String cppDir) {
        this.cppDir = cppDir;
        return this;
    }

    public ProtoGenTool setProtoc(String protoc) {
        this.protoc = protoc;
        return this;
    }

    public ProtoGenTool registerProto(Class<?> proto) {
        protos.add(proto);
        return this;
    }

    public void gen() {
        for (Class<?> proto : protos) {
            String x = ProtoGen.gen(proto);
            System.out.println(x);
        }
    }

}
