package com.ron.controller;

import com.ron.dto.Exposer;
import com.ron.dto.SeckillExecution;
import com.ron.dto.SeckillResult;
import com.ron.entity.Seckill;
import com.ron.enums.SeckillStatEnum;
import com.ron.exception.RepeatKillException;
import com.ron.exception.SeckillCloseException;
import com.ron.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 秒杀商品的控制层
 *
 * @auther Ron
 * @date 2019/10/6
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private SeckillService seckillService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/list")
    public String findSeckillList(Model model) {
        List<Seckill> list = seckillService.findAll();
        model.addAttribute("list", list);
        return "page/seckill";
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Seckill findById(@RequestParam("id") Long id) {
        return seckillService.findById(id);
    }

    @RequestMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "page/seckill";
        }
        Seckill seckill = seckillService.findById(seckillId);
        model.addAttribute("seckill", seckill);
        if (seckill == null) {
            return "page/seckill";
        }
        return "page/seckill_detail";
    }

    /**
     * @param seckillId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/{seckillId}/exposer", produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{seckillId}/{md5}/execution", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @RequestParam("money") BigDecimal money,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, money, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
    }

    @ResponseBody
    @GetMapping(value = "/time/now")
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }

}
