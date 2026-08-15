# SUPPLYAI 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

Vue 3 管理端和 H5 工作台通过 JWT 调用 Spring Boot REST API。领域服务 `SupplyRiskService` 组合准时交付、来料质量、采购提前期、地区风险、单一来源依赖和库存覆盖，输出供应风险、缓冲天数与保障建议；JPA 与 Flyway 管理 MySQL 数据，Docker Compose 负责本地编排。

生产落地时应接入企业 SSO、SRM、ERP、WMS、TMS、供应商主数据与企业模型网关，并对供应商切换、加急采购和库存调整保留人工授权与审计证据。
