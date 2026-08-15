/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.supplyai.config;
import cn.zhuatech.supplyai.model.*; import cn.zhuatech.supplyai.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository orders,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
 var u1=units.save(new OperatingUnit("SUPPLY-RISK","集团供应风控组","供应链中心",180));var u2=units.save(new OperatingUnit("PURCHASE-EAST","华东采购中心","采购中心",120));var u3=units.save(new OperatingUnit("SUPPLY-ASSURE","供应保障组","运营中心",96));
 var t1=orders.save(new WorkRecord("SR-260815-018","MAT-BRG-017","精密轴承断供风险评估",u1,24,16,1,LocalDate.now().plusDays(1),WorkRecord.Status.RUNNING,"SRM+库存"));
 var t2=orders.save(new WorkRecord("SR-260815-021","SUP-HD-083","华东电子料交付稳定性",u2,18,8,0,LocalDate.now().plusDays(2),WorkRecord.Status.RUNNING,"订单+物流"));
 var t3=orders.save(new WorkRecord("SR-260815-026","MAT-SEAL-032","进口密封件替代资源评估",u1,12,0,0,LocalDate.now().plusDays(3),WorkRecord.Status.RELEASED,"供应商+替代"));
 var t4=orders.save(new WorkRecord("SR-260814-015","SUP-HN-026","华南包装材料履约复盘",u3,20,20,1,LocalDate.now(),WorkRecord.Status.COMPLETED,"订单+质检"));
 resources.saveAll(List.of(new ResourceRegister("SRM-01","供应商协同平台",u1,ResourceRegister.Status.RUNNING,99),new ResourceRegister("MODEL-02","供应中断风险模型",u2,ResourceRegister.Status.IDLE,94),new ResourceRegister("TMS-03","物流在途数据连接器",u2,ResourceRegister.Status.RUNNING,92),new ResourceRegister("ALT-04","替代资源服务",u3,ResourceRegister.Status.ALARM,88)));
 reviews.saveAll(List.of(new ReviewRecord("RV-260804-032",t1,"人工复核",6,0,ReviewRecord.Result.PASSED,"程越"),new ReviewRecord("RV-260804-011",t2,"质量检查",3,0,ReviewRecord.Result.PASSED,"许知"),new ReviewRecord("RV-260803-018",t4,"结果抽查",5,1,ReviewRecord.Result.FAILED,"程越"),new ReviewRecord("RV-260804-003",t3,"上线确认",4,0,ReviewRecord.Result.PENDING,"许知")));
 String demo=encoder.encode("Demo@2026");users.saveAll(List.of(new UserAccount("operator",demo,"许知",UserAccount.Role.DOMAIN_USER,"SEARCH-OPS"),new UserAccount("planner",demo,"程越",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"顾清",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));};}
}
