package io.gitee.zhousiwei.controller;

import io.gitee.zhousiwei.entity.Test;
import io.gitee.zhousiwei.repository.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.*;

/**
 * 测试
 *
 * @author Created by 試毅-思伟 on 2018/8/17
 */
@RestController("/")
@Slf4j
public class TestController {

    private Integer min = 1;
    private Integer max = 10000;
    private SecureRandom random = new SecureRandom();

    @Resource
    private TestRepository testRepository;
    //@Resource
    //private org.springframework.data.elasticsearch.core.ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping(value = {"/", ""})
    public Object index() {
        long count = testRepository.count();
        log.info("ELasticSearch索引总数:{}", count);
        return count;
    }

    @GetMapping(value = "/list")
    public Object list() {
        Iterable<Test> all = testRepository.findAll();
        List<Test> test = new ArrayList<Test>();
        all.forEach(single -> {
            test.add(single);
        });
        return new ResponseEntity(test, HttpStatus.OK);
    }

    @GetMapping("/add")
    public Object add() {
        Test test = new Test();
        Long rangeLong = min + (((long) (random.nextDouble() * (max - min))));
        Integer rangeInteger = min + (random.nextInt() * (max - min));
        test.setId(rangeLong);
        test.setContent("这是一条普通内容");
        test.setCreateTime(new Date());
        test.setUpdateTime(new Date());
        test.setName("This is 雾都");
        test.setReadNumber(rangeInteger);
        Test save = testRepository.save(test);
        return new ResponseEntity(save, HttpStatus.OK);
    }

    @GetMapping("/get")
    public Object get(Long id) {
        Optional<Test> byId = testRepository.findById(id);
        //是否存在
        if (byId.isPresent()) {
            return new ResponseEntity(byId.get(), HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/del")
    public Object del(Long id) {
        testRepository.deleteById(id);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("code", 200);
        map.put("msg", "删除成功");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/update")
    public Object update(Long id) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        if (!testRepository.existsById(id)) {
            map.put("code", 500);
            map.put("msg", "更新失败");
            return new ResponseEntity(map, HttpStatus.OK);
        }
        Test test = new Test();
        Integer rangeInteger = min + (random.nextInt() * (max - min));
        test.setId(id);
        test.setContent("更新后的内容");
        test.setCreateTime(new Date());
        test.setUpdateTime(new Date());
        test.setName("更新后的This is 雾都");
        test.setReadNumber(rangeInteger);
        Test save = testRepository.save(test);
        log.debug(save.toString());
        map.put("code", 200);
        map.put("msg", "更新成功");
        return new ResponseEntity(map, HttpStatus.OK);
    }

}
