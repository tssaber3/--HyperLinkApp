package com.us.demo.base;

import java.util.List;

/*
 * 服务类通用接口
 * 返回多个对象
 */
public class ServiceMultiResult<T>//此处可以随便写标识符号
{
    //数据的个数  也就是list的长度
    private long total;

    private List<T> result;

    public ServiceMultiResult(long total,List<T> result)
    {
        this.total = total;
        this.result = result;
    }

    //
    //得到 list的长度
    public int getResultSize()
    {
        if(this.result == null)
        {
            return 0;
        }
        return this.result.size();
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
