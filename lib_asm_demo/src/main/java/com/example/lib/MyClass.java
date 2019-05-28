package com.example.lib;


import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;


public class MyClass {

    public static void main(String[] args) {
        println("**********************开始修改**********************");
        try {
            String userCls = "com.example.lib.Log";
            String path = ClassLoader.getSystemResource(userCls.replace('.', '/') + ".class").getFile();
            File file = new File(path);

            ClassReader cr = new ClassReader(userCls);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

            byte[] data = cw.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        println("**********************修改后**********************");
        new Log().show();
    }

    static class AddSecurityCheckClassAdapter extends ClassAdapter {
        public AddSecurityCheckClassAdapter(ClassVisitor cv) {
            super(cv);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name,
                                         final String desc, final String signature, final String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            println("visitMethod-->" + access + " " + name + " " + desc);
            if (mv != null) {
                if (access == Opcodes.T_SHORT
                        && "show".equals(name)
                        && "()V".equals(desc)) {
                    // 使用自定义 MethodVisitor，实际改写方法内容
                    return new AddCodeMethodVisitor(mv);
                }
            }
            return mv;
        }
    }

    static class AddCodeMethodVisitor extends MethodAdapter {
        public AddCodeMethodVisitor(MethodVisitor mv) {
            super(mv);
        }

        @Override
        public void visitCode() {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("方法开始运行");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            super.visitCode();
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("方法调用结束");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            }
            super.visitInsn(opcode);
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }
}
