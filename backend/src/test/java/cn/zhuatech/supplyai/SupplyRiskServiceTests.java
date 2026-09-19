/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.supplyai;
import cn.zhuatech.supplyai.service.SupplyRiskService; import org.junit.jupiter.api.Test; import static org.assertj.core.api.Assertions.assertThat;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class SupplyRiskServiceTests{private final SupplyRiskService service=new SupplyRiskService();/**
                                                                                              * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                              */
@Test void escalatesCriticalSupplier(){var r=service.assess(new SupplyRiskService.Request("SUP-AX17",68,91,62,74,88,6));assertThat(r.riskLevel()).isEqualTo("CRITICAL");assertThat(r.recommendedBufferDays()).isEqualTo(21);assertThat(r.riskSignals()).hasSizeGreaterThanOrEqualTo(4);}/**
                                                                                                                                                                                                                                                                                                                                                                                      * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                      */
@Test void keepsReliableSupplierStable(){var r=service.assess(new SupplyRiskService.Request("SUP-BK08",97,99,14,12,20,32));assertThat(r.riskLevel()).isEqualTo("STABLE");assertThat(r.recommendedBufferDays()).isEqualTo(7);}}
