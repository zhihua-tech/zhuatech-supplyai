/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.supplyai.controller;
import cn.zhuatech.supplyai.common.ApiResponse; import cn.zhuatech.supplyai.service.SupplyRiskService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ai/supply") @PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')") public class SupplyRiskController{private final SupplyRiskService service;public SupplyRiskController(SupplyRiskService service){this.service=service;}@PostMapping("/assess") public ApiResponse<SupplyRiskService.Result> assess(@Valid @RequestBody SupplyRiskService.Request request){return ApiResponse.ok("供应风险评估完成",service.assess(request));}}
