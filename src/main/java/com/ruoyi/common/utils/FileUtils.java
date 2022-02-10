package com.ruoyi.common.utils;

import java.io.*;

/**
 * Created by Fyc on 2021-3-17.
 * 文件工具类
 */
public class FileUtils
{
    public static byte[] toByteArray(InputStream input) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer)))
        {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 将文件转换成byte数组
     *
     * @param tradeFile
     * @return
     */
    public static byte[] File2byte(File tradeFile)
    {
        FileInputStream fis = null;
        byte[] buffer = null;
        try
        {
            fis = new FileInputStream(tradeFile);
            return toByteArray(fis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                if(null!=fis) fis.close();
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
