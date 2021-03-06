package com.project.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lovo.back.dto.CompanyContractDto;
import com.lovo.back.entity.CompanyContractAndStall;
import com.lovo.back.entity.CompanyContractEntity;
import com.lovo.back.entity.OutContractAndStall;

import com.lovo.back.entity.OutContractEntity;


import com.project.service.ICompanyContractService;
import com.project.service.IComplainService;
import com.project.service.IOutContractService;
import com.project.service.IBackStallService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

@RestController

public class BackController {


    public static String fileName = "";
    public static final String ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    @Autowired
    public BackController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /*车位前端接口*/
    @Autowired
    IBackStallService stallService;
    /*外部合约前端接口*/
    @Autowired
    IOutContractService outContractService;

    /*企业合约前端接口*/
    @Autowired
    ICompanyContractService companyContractService;
    /*投诉业务接口*/
    @Autowired
    IComplainService complainService;

    /**
     * 动态查询车位信息
     */

    @RequestMapping("test")
    public String test(){


        return "ok";
    }

    @PostMapping("stall/findByItems")
    @ResponseBody
    public String findByItems(@RequestBody Map<String, String> map) {

        String str = stallService.findByItmesPage(map);

        System.out.println(str);

        return str;
    }


    /**
     * 查询平台空闲车辆
     *
     * @return
     */
    @GetMapping("stall/findByState")
    @ResponseBody
    public String findByState() {
        return stallService.findByState();
    }


    /**
     * 根据车位id修改车位的拥有者
     */
    @GetMapping("stall/updatePeole")
    @ResponseBody
    public void updatePeopleNo(@RequestBody Map<String, String> map) {


        stallService.updatePeopleNo(map);

    }


    @PostMapping("stall/findByAddress")
    @ResponseBody
    public String findByAddress(@RequestBody String stallAddress) {


        return JSON.toJSONString(stallService.findByAddress(stallAddress));
    }


    @PostMapping("stall/add")
    @ResponseBody
    public String addStall(@RequestBody Map<String, String> map) {
        stallService.add(map);
        return "ok";
    }


    /**
     * 分页查询外部合约
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return
     */
    @GetMapping("outContract/findAll")
    @ResponseBody
    public String findAllOutContract(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {


        return outContractService.findAllOutContract(currentPage, pageSize);

    }


    @GetMapping("outContract/findById")
    @ResponseBody
    public String findOutContractById(@RequestParam("id") String id) {
        return outContractService.findById(id);

    }


    @PostMapping("outContract/add")
    @ResponseBody
    public String addOutContract(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        map.put("contractImg", fileName);
        outContractService.addCompanyContract(map);
        return "ok";
    }


    @PostMapping("file")
    @ResponseBody
    public String getFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        try {
            //获取上传的流
            InputStream inputStream = file.getInputStream();
            /*将上传文件保存*/

            name = name.substring(name.lastIndexOf("."));
            name = UUID.randomUUID().toString().replace("-", "") + name;

            File dir = new File("CBDWeb/classpath:/static/" + ROOT);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            file.transferTo(Paths.get(ROOT, name));

        } catch (IOException e) {
            e.printStackTrace();
        }


        fileName = name;

        return "ok";
    }

    @PostMapping("outContract/goOn")
    public String goOnOutContract(@RequestBody Map<String, Object> map) {

        /*获取续约前的合同*/
        String jsonOutContract = outContractService.findById((Integer) map.get("id") + "");
        OutContractEntity outContractDto1 = JSONObject.parseObject(jsonOutContract, OutContractEntity.class);

        /*获取之前的车位*/
        List<Integer> stallIdList = new ArrayList<Integer>();
        for (OutContractAndStall obj : outContractDto1.getOutContractAndStallSet()) {

            int stallId = obj.getStall().getId();

            stallIdList.add(stallId);
        }
        map.put("contractImg", fileName);
        map.put("unit", outContractDto1.getUnit());
        map.put("stallIdList", stallIdList);
        outContractService.addCompanyContract(map);

        return "ok";
    }

    @GetMapping("outContract/updateState")
    public String updateOutContractState(String id) {

        outContractService.updateState(id);

        return "ok";
    }


    /**
     * 分页查询 企业合约
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return
     */
    @GetMapping("companyContract/findAll")
    @ResponseBody
    public String findAllCompanyContract(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {

        return companyContractService.findAllCompanyContract(currentPage, pageSize);

    }

    @GetMapping("companyContract/findById")
    @ResponseBody
    public String findCompanyContractById(@RequestParam("id") String id) {
        return companyContractService.findById(id);
    }


    @PostMapping("companyContract/add")
    public String addCompanyContract(@RequestBody Map<String, Object> map) {

        companyContractService.addCompanyContract(map);

        return "ok";
    }


    @GetMapping("companyContract/updateState")
    public String updateCompanyState(String id) {

        companyContractService.updateState(id);

        return "ok";
    }


    @PostMapping("companyContract/goOn")
    public String goOnCompanyContract(@RequestBody Map<String, Object> map) {

        String id = (String) map.get("id");

        //获得续约合同的原合同
        String jsonOutContract = companyContractService.findById(id);
        CompanyContractEntity companyContractDto = JSONObject.parseObject(jsonOutContract, CompanyContractEntity.class);
        String companyName = companyContractDto.getCompanyName();
        //生成新的合同
        CompanyContractDto companyContractDto1 = new CompanyContractDto();
        //得到之前合同的车位id集合

        List<Integer> stallIdList = new ArrayList<Integer>();
        for (CompanyContractAndStall obj : companyContractDto.getSet()) {

            int stallId = obj.getStall().getId();

            stallIdList.add(stallId);
        }


        map.put("companyName", companyName);
        map.put("stallIdList", stallIdList);
        companyContractService.addCompanyContract(map);

        return "ok";
    }

    /*查询所有的投诉*/
    @GetMapping("complaint/findAll")
    public String findAllComplaint() {

        return complainService.findAll();

    }

    /*查询id投诉*/
    @GetMapping("complaint/findById")
    public String findComplaintById(int id) {

        return complainService.findById(id);

    }

    /*修改投诉状态*/
    @GetMapping("complaint/updateState")
    public String updateComplaint(int id, int state) {

        complainService.updateComplaintById(id, state);

        return "ok";
    }

}
