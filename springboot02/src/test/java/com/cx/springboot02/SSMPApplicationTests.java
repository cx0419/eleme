package com.cx.springboot02;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.mapper.*;
import com.cx.springboot02.pojo.*;
import com.cx.springboot02.service.impl.BusinessServiceImpl;
import com.cx.springboot02.service.impl.CustomerServiceImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.rocketmq.common.message.Message;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SSMPApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws Exception{
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        System.out.println(dataSource.toString());
        //template模板，拿来即用
        connection.close();
    }

    @Autowired
    BookMapper bookMapper;
    @Test
    public void toTest(){

//        Book book = bookMapper.selectById(1);
//        System.out.println(book.toString());
        List<Book> list = bookMapper.selectList(null);
        System.out.println(list.toString());

    }

    @Test
    public void MPadd(){
        Book book = new Book();
        book.setName("三国演义");
        book.setType("历史");
        book.setDescription("111");
        bookMapper.insert(book);
    }

    @Test
    public void update(){
        Book book = new Book();
        book.setId(2);
        book.setType("小说");
        bookMapper.updateById(book);
    }
    @Test
    public void delete(){
        bookMapper.deleteById(1);

    }

    @Test
    public void FenYeTest(){
        Integer i = 0;
        Page<Book> page = new Page(1,10);
        //方式1
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Book> list = (List<Book>) bookMapper.selectPage(page,queryWrapper);
        //方式2
        QueryWrapper<Book> queryWrapper1 = new QueryWrapper();
        queryWrapper1.lambda().lt(Book::getId, 1);


        //方式3
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        //10 - 30 之间
        lqw.lt(Book::getId, 30).gt(Book::getId,10);
        // <=10 or >=30
        lqw.lt(Book::getId, 10).or().gt(Book::getId, 30);



        List<Book> list1 = bookMapper.selectList(lqw);

    }

    @Test
    public void NullCheck(){
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
//        投影
//        lqw.select(Book::getId,Book::getName,Book::getType);
        lqw.gt(Book::getId, 1).lt(Book::getId,3);
        List<Book> list = bookMapper.selectList(lqw);
        System.out.println(list.toString());
        List<Map<String,Object>> mapList = bookMapper.selectMaps(lqw);

        QueryWrapper<Book> qw = new QueryWrapper();

        qw.select("count(*)");
        List<Map<String,Object>> mapList1 = bookMapper.selectMaps(lqw);
        System.out.println(mapList1.toString());
    }

    /**
     * 多行删除
     */
    @Test
    public void DeleteMuch(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        bookMapper.deleteBatchIds(list);
    }


    @Autowired
    CustomerServiceImpl customerService;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    public void selectCustomer(){
        String email = "123@qq.com";
        String password = "1234";
        LambdaUpdateWrapper<Customer> userQueryWrapper = Wrappers.<Customer>lambdaUpdate()
                .eq(Customer::getEmail,email);
        Customer customer = new Customer();
        customer.setPassword(password);
        customerMapper.update(customer,userQueryWrapper);

    }


    @Autowired
    BusinessServiceImpl businessService;


    @Autowired
    BusinessMapper businessMapper;

    @Test
    public void fenye1(){
        LambdaQueryWrapper<Business> userLambdaQueryWrapper = Wrappers.lambdaQuery();
//        userLambdaQueryWrapper.like(Business::getShopName , "木桶");

        Page<Business> userPage = new Page<>(1 , 10);
        IPage<Business> userIPage = businessMapper.selectPage(userPage , userLambdaQueryWrapper);
        System.out.println("总页数： "+userIPage.getPages());
        System.out.println("总记录数： "+userIPage.getTotal());
        userIPage.getRecords().forEach(System.out::println);
    }


    @Autowired
    AddressMapper addressMapper;


    @Test
    public void  save() {
        Address address = new Address();
        address.setCid(123L);
        address.setSpecificAddress("123挖省掉呢");
        address.setPhone("12313122222");
        address.setLat(123.123);
        address.setLon(123.1);
        address.setName("name");
        addressMapper.insert(address);
    }

    public void  bis() {
        RPage<Business> businessListPage = businessService.getBusinessListPage(0, 1);

        System.out.println(businessListPage.toString());
    }


    @Autowired
    BuyCartMapper buyCartMapper;


    @Autowired
    OrderMapper orderMapper;
    @Test
    public void Test10(){
        Order order = new Order();
        order.setCustomerId(232L);
        orderMapper.insertOrder(order);
    }


    @Test
    public void TR() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("myproducer_group_topic_name_delay_01");
        //设置实例名称，一个jvm中有多个生产者可以根据实例名区分
        //默认default
        producer.setInstanceName("topic_delay");
        // 指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");
        //设置同步重试次数
        producer.setRetryTimesWhenSendFailed(2);
        //设置异步发送次数
        //producer.setRetryTimesWhenSendAsyncFailed(2);
        // 初始化生产者
        producer.start();
        for (int i = 0; i <20 ; i++) {
            Message message = new Message("topic_name_delay", ("key=" + i).getBytes("utf-8"));
            //设置延迟消费时间 设置延迟时间级别0,18,0表示不延迟，18表示延迟2h，大于18的都是2h
            message.setDelayTimeLevel(i);
            // 1 同步发送  如果发送失败会根据重试次数重试
            SendResult send = producer.send(message);
            SendStatus sendStatus = send.getSendStatus();
            System.out.println(sendStatus.toString());

        }
    }


//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Test
//    public void testRocketMq1() {
//        Order order = new Order();
//        order.setId(1L);
//        rocketMQTemplate.asyncSend("WALLET_ORDER_TOPIC", MessageBuilder.withPayload(order).build(), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult var1) {
//                System.out.println("async onSucess SendResult :{}"+var1);
//            }
//
//            @Override
//            public void onException(Throwable var1) {
//                System.out.println("async onException Throwable :{}"+var1);
//            }
//
//        }, 300000, 2);
//        }

}
