package com.keelin.permission;

/**
 * 使用位运算进行权限控制
 */
public class Permission {

    // 是否允许查询，二进制第1位，0表示否，1表示是
    public static final int ALLOW_SELECT = 1 << 0; // 0001  = 1  0001

    // 是否允许新增，二进制第2位，0表示否，1表示是
    public static final int ALLOW_INSERT = 1 << 1; // 0010  = 2  0010

    // 是否允许修改，二进制第3位，0表示否，1表示是
    public static final int ALLOW_UPDATE = 1 << 2; // 0100  =4   0100

    // 是否允许删除，二进制第4位，0表示否，1表示是
    public static final int ALLOW_DELETE = 1 << 3; // 1000  = 8  1000
    // 存储目前的权限状态
    private int flag;
    //设置用户的权限
    public void setPer(int per) {
        flag = per;
    }
    //增加用户的权限（1个或者多个）
    public void enable(int per) {
        flag = flag|per;
    }
    //删除用户的权限（1个或者多个）
    public void disable(int per) {
        flag = flag&~per;
    }
    //判断用户的权限
    public boolean isAllow(int per) {
        return ((flag&per)== per);
    }
    //判断用户没有的权限
    public boolean isNotAllow(int per) {
        return ((flag&per)==0);
    }

    public static void main(String[] args) {
        int flag = 15;  //所有的权限都有的话 就是 1+2+4+8 = 15
        System.out.println(Integer.toBinaryString(15));
        Permission permission = new Permission();
        permission.setPer(flag);
        permission.disable(ALLOW_DELETE|ALLOW_INSERT);   //1010&~1111=0101
        System.out.println("select = "+permission.isAllow(ALLOW_SELECT)); //0101&0001=0001
        System.out.println("update = "+permission.isAllow(ALLOW_UPDATE)); //0101&0100=0100
        System.out.println("insert = "+permission.isAllow(ALLOW_INSERT)); //0101&0010=0000
        System.out.println("delete = "+permission.isAllow(ALLOW_DELETE)); //0101&1000=0000

        permission.enable(ALLOW_INSERT);//0101|0010=0111
        System.out.println("select = "+permission.isAllow(ALLOW_SELECT));
        System.out.println("update = "+permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert = "+permission.isAllow(ALLOW_INSERT)); //0111&0010=0010
        System.out.println("delete = "+permission.isAllow(ALLOW_DELETE));
    }
}
