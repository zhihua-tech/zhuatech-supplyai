/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.supplyai.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** 依据可解释规则完成 AI 系统上线前风险分级，不依赖外部模型或密钥。 */
@Service
public class AiRiskAssessmentService {
    public record Request(@NotBlank String systemName, @NotBlank String useCase,
                          @Min(0) @Max(3) int autonomyLevel, boolean handlesPersonalData,
                          boolean humanOversight, boolean externalImpact,
                          boolean evaluated, boolean transparencyNotice) {}
    public record Result(String systemName, int riskScore, String riskTier,
                         String releaseDecision, List<String> requiredControls,
                         List<String> evidenceChecklist) {}

    public Result assess(Request request) {
        int score = 15 + request.autonomyLevel() * 18;
        List<String> controls = new ArrayList<>();
        List<String> evidence = new ArrayList<>(List.of("用途与责任人登记", "模型及数据版本记录"));
        if (request.handlesPersonalData()) { score += 18; controls.add("完成个人信息影响评估与最小化设计"); }
        if (request.externalImpact()) { score += 16; controls.add("配置对外输出标识、申诉与人工复核通道"); }
        if (!request.humanOversight()) { score += 20; controls.add("为关键决策增加人工审批和紧急停止机制"); }
        if (!request.evaluated()) { score += 15; controls.add("完成准确性、偏见、鲁棒性与越权测试"); }
        if (!request.transparencyNotice()) { score += 8; controls.add("补充 AI 使用告知和能力边界说明"); }
        score = Math.min(score, 100);
        if (request.handlesPersonalData()) evidence.add("数据来源、授权依据与保留周期");
        if (request.externalImpact()) evidence.add("用户告知、反馈与事件响应方案");
        String tier = score >= 75 ? "HIGH" : score >= 45 ? "MEDIUM" : "LOW";
        String decision = score >= 75 ? "BLOCK" : score >= 45 ? "REVIEW" : "PASS";
        if (controls.isEmpty()) controls.add("保持季度复评并记录运行指标");
        return new Result(request.systemName(), score, tier, decision, controls, evidence);
    }
}
