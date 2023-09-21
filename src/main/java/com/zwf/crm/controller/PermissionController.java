package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-15 17:01
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
      @Resource
    private PermissionService permissionService;


}