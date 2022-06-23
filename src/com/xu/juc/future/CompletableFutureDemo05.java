package com.xu.juc.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * CompletableFutable比价小程序
 */
public class CompletableFutureDemo05 {
    static List<NetMall> mallList = Arrays.asList(
            new NetMall("taobao"),
            new NetMall("jingdong"),
            new NetMall("pinxixi"),
            new NetMall("xianyu")
    );
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long time = new Date().getTime();
//        List<String> mysql = getPrice(mallList, "MYSQL");
        Future future = null;
        for (NetMall netMall : mallList) {
            future = getPrice2(netMall, "MYSQL");
        }
        future.get();
        System.out.println(new Date().getTime() - time+" 毫秒");
    }

    public static List<String> getPrice(List<NetMall> netMalls,String bookName) {
//        return netMalls.stream().map(netMall ->
//                        CompletableFuture.supplyAsync(() -> String.format(bookName + " in %s price is %.2f", netMall.getName(), netMall.getPrice(bookName))))
//                .collect(Collectors.toList()).stream().map(CompletableFuture::join).collect(Collectors.toList());
        return mallList.stream().map(netMall ->{
            return CompletableFuture.supplyAsync(()->{

                    return String.format(bookName+" in %s price is %.2f", netMall.getName(),netMall.getPrice(bookName));
            });
        }).collect(Collectors.toList()).stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
    public static Future getPrice2(NetMall netMall,String bookName) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<?> submit = executorService.submit(() -> {
            System.out.println(String.format(bookName + " in %s price is %.2f", netMall.getName(), netMall.getPrice(bookName)));
        });
        executorService.shutdown();
        return submit;
    }

}

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Accessors(chain = true)
class NetMall{
    @Getter
    private String name;

    public NetMall(String name){
        this.name = name;
    }

    public Double getPrice(String bookName)  {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble(10) *2+bookName.charAt(0);
    }
}
