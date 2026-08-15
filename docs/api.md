# SUPPLYAI API 摘要

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/admin/dashboard` | 知华供应链风险 AI 平台运营控制台 |
| GET | `/api/admin/work-orders` | 供应风险评估任务列表 |
| GET | `/api/shopfloor/dashboard` | 供应保障专员工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交供应保障反馈 |
| POST | `/api/ai/supply/assess` | 供应风险、缓冲天数和保障建议 |
| POST | `/api/shopfloor/ai-risk-assessment` | AI 功能上线风险初筛 |

除登录外均需 `Authorization: Bearer <token>`。社区演示实现不调用外部模型，不需要 API Key。
