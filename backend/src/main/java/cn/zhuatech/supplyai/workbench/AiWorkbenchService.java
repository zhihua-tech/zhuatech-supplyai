/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.supplyai.workbench;

import static cn.zhuatech.supplyai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "asOf", "lines");
    var asOf = date(text(r, "asOf", 10));
    Set<String> ids = new HashSet<>();
    Map<String, int[]> counts = new TreeMap<>();
    List<Map<String, Object>> out = new ArrayList<>();
    for (var x : rows(r, "lines", 1, 500)) {
      keys(x, "id", "supplier", "dueDate", "receivedDate", "ordered", "received");
      String id = id(x, "id");
      unique(ids, id);
      String supplier = id(x, "supplier");
      var due = date(text(x, "dueDate", 10));
      Object raw = x.get("receivedDate");
      if (!(raw instanceof String s)) throw new IllegalArgumentException("收货日期必须为日期或空字符串");
      var receivedOn = s.isEmpty() ? null : date(s);
      if (receivedOn != null && receivedOn.isAfter(asOf))
        throw new IllegalArgumentException("不能登记未来已收货日期");
      var ordered = decimal(x, "ordered", 0.000001, 1e9);
      var qty = decimal(x, "received", 0, 1e9);
      if (receivedOn == null && qty.signum() != 0)
        throw new IllegalArgumentException("已有收货数量必须提供收货日期");
      boolean eligible = !due.isAfter(asOf),
          full = qty.compareTo(ordered) >= 0,
          onTime = receivedOn != null && !receivedOn.isAfter(due);
      String status =
          !eligible
              ? "NOT_DUE"
              : receivedOn == null
                  ? "OVERDUE_NOT_RECEIVED"
                  : onTime && full ? "OTIF" : onTime ? "SHORT_DELIVERY" : "LATE_DELIVERY";
      var c = counts.computeIfAbsent(supplier, k -> new int[4]);
      if (eligible) {
        c[0]++;
        if (onTime) c[1]++;
        if (full) c[2]++;
        if (onTime && full) c[3]++;
      }
      out.add(
          Map.of(
              "id",
              id,
              "supplier",
              supplier,
              "status",
              status,
              "includedInRate",
              eligible,
              "onTime",
              onTime,
              "inFull",
              full));
    }
    List<Map<String, Object>> summaries = new ArrayList<>();
    counts.forEach(
        (supplier, c) ->
            summaries.add(
                Map.of(
                    "supplier",
                    supplier,
                    "dueLines",
                    c[0],
                    "rateDefined",
                    c[0] > 0,
                    "onTimePercent",
                    c[0] == 0 ? BigDecimal.ZERO : round(c[1] * 100.0 / c[0]),
                    "inFullPercent",
                    c[0] == 0 ? BigDecimal.ZERO : round(c[2] * 100.0 / c[0]),
                    "otifPercent",
                    c[0] == 0 ? BigDecimal.ZERO : round(c[3] * 100.0 / c[0]))));
    return Map.of("suppliers", summaries, "lines", out);
  }
}
