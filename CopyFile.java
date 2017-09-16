import java.io.*;
public class CopyFile
{
    public static void copyFile(String pathName1,String pathName2) throws Exception
    {
        File file1 = new File(pathName1);
        File file2 = new File(pathName2);
        if(!file1.isDirectory())  //先判断复制的是不是文件夹
        {
            if(file1.exists() && !(file2.exists()))   //如果复制的是文件不是文件夹，而且file2在目标路径不存在同名
            {
                FileInputStream input = new FileInputStream(file1);
                FileOutputStream output = new FileOutputStream(file2);

                int i;
                do{
                    byte[] byteData = new byte[1024];
                    i = input.read(byteData);
                    if(file2.createNewFile())
                    {
                        output.write(byteData);
                        System.out.println("文件创建成功");
                    }else
                    {
                        output.write(byteData);
                    }
                }while(i != -1);
                output.close();
                input.close();
            }else if(!file1.exists())
            {
                System.out.println("复制目标文件不存在");
            }else if(file2.exists())       //目标文件路径已有名字同名，复制失败
            {
                System.out.println("目标文件夹已有这个名字文件，操作失败");
            }
        }else      //如果是就判断目标文件名是否存在，存在返回错误，因为不允许同名的文件
        {
            if(!file2.exists())
            {
                if(file2.mkdir())
                {
                    System.out.println("创建文件夹成功");
                }
            }

            if(file2.isDirectory())   //判断file1是否是文件夹，如果是就需要多次复制
            {
                File[] files = file1.listFiles();   //将file1的文件名存入files数组
                for(int i=0; i<files.length; i++)
                {
                    String file2Name = file2.getPath() + "/" + files[i].getName();  //将目标路径加上复制文件名字复制文件
                    System.out.println(file2Name);
                    copyFile(files[i].getPath(),file2Name);    //递归调用复制
                }
            }else
            {
                System.out.println("复制的是文件夹，但目标文件不是文件夹");
            }
        }           
    }
    public static void main(String[] args) throws Exception
    {
        copyFile(args[0],args[1]); //从终端输入路径
    }
}
