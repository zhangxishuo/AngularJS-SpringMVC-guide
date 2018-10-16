package com.mengyunzhi.SpringMvcStudy.controller;

import com.mengyunzhi.SpringMvcStudy.entity.Klass;
import com.mengyunzhi.SpringMvcStudy.entity.Teacher;
import com.mengyunzhi.SpringMvcStudy.repository.KlassRepository;
import com.mengyunzhi.SpringMvcStudy.repository.TeacherRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author panjie on 2017/11/28
 */
public class TeacherControllerTest extends ControllerTest {
    private final static Logger logger = LoggerFactory.getLogger(TeacherControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;    // 模拟进行REST请求
    @Autowired
    TeacherRepository teacherRepository;    // 教师
    @Autowired
    KlassRepository klassRepository; // 班级

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void getAllTest() throws Exception {
        logger.info("init teacher data");
        Teacher teacher = new Teacher();
        teacher.setSex(true);
        teacher.setEmail("test@yunzhiclub.com");
        teacher.setName("test teacher name");
        teacher.setUsername("test+sdfsdfefwefwe'sl;fmsdlfml");
        teacherRepository.save(teacher);

        logger.info("init klass data");
        Klass klass = new Klass();
        klass.setName("test name");
        klass.setTeacher(teacher);
        klassRepository.save(klass);

        String url = "/Teacher";
        this.mockMvc
                .perform(get(url))  // 用get方法来请求这个url
                .andDo(print()) // 请求后，打印请求的返回数据
                .andExpect(status().isOk()); // 断言返回的状态为真
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void getTest() throws Exception {
        // 创建一条数据
        logger.info("init teacher data");
        Teacher teacher = new Teacher();
        teacher.setSex(true);
        teacher.setEmail("test@yunzhiclub.com");
        teacher.setName("test teacher name");
        teacher.setUsername("test+sdfsdfefwefwe'sl;fmsdlfml");
        teacherRepository.save(teacher);

        logger.info("init klass data");
        Klass klass = new Klass();
        klass.setName("test name");
        klass.setTeacher(teacher);
        klassRepository.save(klass);

        String url = "/Teacher/" + String.valueOf(teacher.getId());
        this.mockMvc
                .perform(get(url))  // 用get方法来请求这个url
                .andDo(print()) // 请求后，打印请求的返回数据
                .andExpect(status().isOk()); // 断言返回的状态为真
    }

    @Test
    public void updateTest() throws Exception {
        // 添加测试数据
        // 实例化一个Teacher并且持久化
        Teacher teacher = new Teacher();
        teacherRepository.save(teacher);

        // 更新这个持久化的Teacher
        String url = "/Teacher/" + teacher.getId();
        this.mockMvc
                .perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}"))  // 用get方法来请求这个url
                .andDo(print()) // 请求后，打印请求的返回数据
                .andExpect(status().isOk()); // 断言返回的状态为真
    }

    @Test
    public void deleteTest() throws Exception {
        // 首先添加一个数据
        Teacher teacher = new Teacher();
        teacherRepository.save(teacher);
        Long id = teacher.getId();

        // 删除这个数据
        String url = "/Teacher/" + teacher.getId();
        this.mockMvc
                .perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()) // 请求后，打印请求的返回数据
                .andExpect(status().isOk()); // 断言返回的状态为真

        // 断言这个删除这个删除成功（查找的时候，查不到了)
        Teacher newTeacher = teacherRepository.findOne(id);
        assertThat(newTeacher).isNull();
    }

}