/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.supplyai.repository; import cn.zhuatech.supplyai.model.ResourceRegister; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ResourceRegisterRepository extends JpaRepository<ResourceRegister,Long>{List<ResourceRegister> findAllByOrderByCodeAsc();long countByStatus(ResourceRegister.Status status);}
