/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.supplyai.controller;
import cn.zhuatech.supplyai.common.ApiResponse; import cn.zhuatech.supplyai.service.SupplyRiskService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/ai/supply") @PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')") public class SupplyRiskController{private final SupplyRiskService service;/**
                                                                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                  */
public SupplyRiskController(SupplyRiskService service){this.service=service;}/**
                                                                                                                                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                               */
@PostMapping("/assess") public ApiResponse<SupplyRiskService.Result> assess(@Valid @RequestBody SupplyRiskService.Request request){return ApiResponse.ok("供应风险评估完成",service.assess(request));}}
