package com.mlk.web.home.manager.controller;

import com.mlk.web.home.manager.utils.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 验证码服务
 *
 * @author malikai
 * @date 2021-5-26 9:51
 */
@Controller
@RequestMapping("/veryCode")
@Api(tags = "验证码服务")
public class VeryCodeController {

    @RequestMapping(value = "/drawImage", method = RequestMethod.GET)
    @ApiOperation(value = "生成验证码")
    public void getVeryCode(HttpServletRequest req, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", codeMap.get("code").toString());

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
    @ApiOperation(value = "校验验证码")
    public void checkCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String code) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String sessionCode = request.getSession().getAttribute("code").toString();
        if (code != null && !"".equals(code) && sessionCode != null && !"".equals(sessionCode)) {
            if (code.equalsIgnoreCase(sessionCode)) {
                //验证成功
                response.getWriter().println(1);
            } else {
                //验证失败
                response.getWriter().println(0);
            }
        } else {
            //验证失败
            response.getWriter().println(0);
        }
    }
}
