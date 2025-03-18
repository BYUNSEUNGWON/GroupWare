package com.bsw.groupware.document.controller;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bsw.groupware.document.service.DocumentService;
import com.bsw.groupware.mapper.LoginMapper;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class DocumentController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	LoginMapper loginMapper;
	
	
	@RequestMapping("/document.ex")
	public String approveList(HttpSession session, Model model) {
	    String user = (String) session.getAttribute("user");
	    logger.debug("user value :: {}", user);
		return "/document/approvalList";
	}
	
    @PostMapping("/getApprovalForms.ex")
    public ResponseEntity<List<ApprovalFormVO>> ApprovalForms() throws Exception {
        try {
            List<ApprovalFormVO> forms = documentService.getformList();
            logger.debug("forms :: {}", forms);
            return ResponseEntity.ok().body(forms);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping("/submit.ex")
	public String submit(HttpSession session, Model model, String formId) {
	    String user = (String) session.getAttribute("user");
	    UserVO userVO = loginMapper.findByUsername(user);
	    
	    String contents = documentService.getContents(formId);
	    if("".equals(contents) || contents == null) {
	    	contents = "";
	    }
	    model.addAttribute("formContent", contents);
	    model.addAttribute("user", userVO);
		return "/document/submit";
	}
    
    @RequestMapping("/apprLine.ex")
    public String selApprLine(HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");
        List<UserVO> apprLine = documentService.getApprLine(user);
        logger.debug("forms value :: {}", apprLine);
        model.addAttribute("apprLine", apprLine);
        return "/document/apprLine";
    }
    
    @PostMapping("/submitForm.ex")
    public String handleFormSubmission(
            @RequestParam("submitter") String submitter,
            @RequestParam("approver") String approver,
            @RequestParam("editorContent") String editorContent,
            @RequestParam("attachments") MultipartFile[] attachments,
            RedirectAttributes redirectAttributes) {

        List<String> attachmentPaths = new ArrayList<>();

        try {
            for (MultipartFile file : attachments) {
                if (!file.isEmpty()) {
                    // 파일 저장 경로 설정
                    String uploadDir = "/path/to/upload/directory";  // 원하는 경로로 변경
                    File saveFile = new File(uploadDir, file.getOriginalFilename());
                    file.transferTo(saveFile);
                    attachmentPaths.add(saveFile.getAbsolutePath());
                }
            }
            redirectAttributes.addFlashAttribute("message", "성공적으로 제출되었습니다.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "제출 실패: " + e.getMessage());
            e.printStackTrace();
        }

        // 성공 후 리다이렉트
        return "redirect:/document.ex";
    }
    
}
