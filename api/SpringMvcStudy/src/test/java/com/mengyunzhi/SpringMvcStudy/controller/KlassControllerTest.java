package com.mengyunzhi.SpringMvcStudy.controller;

import com.mengyunzhi.SpringMvcStudy.repository.Klass;
import com.mengyunzhi.SpringMvcStudy.repository.KlassRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author panjie on 2017/12/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class KlassControllerTest {
    static final String url = "/Klass/";

    @Autowired
    private MockMvc mockMvc;    // 模拟进行REST请求

    @Autowired
    private KlassRepository klassRepository; // 班级

    @Test
    public void saveTest() throws Exception {
        this.mockMvc
                .perform(post(url)
                        .content("{}")
                        .header("content-type", MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is(201));
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc
                .perform(get(url)
                        .header("content-type", MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdTest() throws Exception {
        // 持久化一个实体到数据表
        Klass klass = new Klass();
        // 为这个班级设置一个随机的字符串
        String name = "sdfsdfdfdfsdfsdfsdfdfsdfd";
        klass.setName(name);
        klassRepository.save(klass);

        // 获取这个持久化的实体
        String getUrl = url + klass.getId().toString();

        this.mockMvc
                .perform(get(getUrl)
                        .header("content-type", MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

    }

    /**
     * 更新的测试
     *
     * @throws Exception
     */
    @Test
    public void updateTest() throws Exception {
        // 持久化一个实体到数据表
        Klass klass = new Klass();
        klassRepository.save(klass);

        // 发起http请求，来更新这个实体\
        String newName = "123";
        String putUrl = url + klass.getId().toString();
        this.mockMvc
                .perform(MockMvcRequestBuilders.put(putUrl)
                        .header("content-type", MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"name\":\"" + newName + "\"}"))
                .andExpect(status().isOk());

        // 断言更新成功(去数据库里找一个这个实体，并获取name，看是否成功)
        Klass newKlass = klassRepository.findOne(klass.getId());
        assertThat(newKlass.getName()).isEqualTo(newName);
    }


}