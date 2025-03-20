package com.bsw.groupware.document.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bsw.groupware.document.service.DocumentService;
import com.bsw.groupware.mapper.LoginMapper;
import com.bsw.groupware.model.ApprDocVO;
import com.bsw.groupware.model.ApprovalFormVO;
import com.bsw.groupware.model.FileMetadata;
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
	
    @Value("${upload.dir}")
    private String baseUploadDir;

	
	@RequestMapping("/document.ex")
	public String approveList(HttpSession session, Model model, @RequestParam(value = "result", required = false) String result) {
	    String user = (String) session.getAttribute("user");
	    
	    if(result != null || result != "") {
	    	model.addAttribute("result", result);
	    }
	    
	    logger.debug("user value :: {}", user);
	    logger.debug("result value :: {}", result);
	    
	    // 결재대기문서 리스트 출력
	    Map<String, Object> params = new HashMap<>();
	    params.put("user", user);
	    params.put("action", "approval");
	    
	    List<ApprDocVO> approvalList = documentService.selectApprovalList(params);
	    
	    model.addAttribute("approvalList", approvalList);

	    
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
    
    @RequestMapping("/commentPopup.ex")
    public String commentPopup(@RequestParam("name") String submitter,
                               @RequestParam("Id") String submitterId,
                               Model model) {
        model.addAttribute("submitter", submitter);
        model.addAttribute("submitterId", submitterId);
        return "/document/commentPopup";
    }
    
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileId") String fileId) {
        // Retrieve file metadata based on fileId from database
        FileMetadata fileMetadata = new FileMetadata();

        Path filePath = Paths.get(fileMetadata.getFilePath());
        File file = filePath.toFile();
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getOriginalFilename() + "\"")
                .body(resource);
    }
    
    @PostMapping("/submitForm.ex")
    public String handleFormSubmission(
            @RequestParam("subject") String subject,
            @RequestParam("submitter") String submitter,
            @RequestParam("submitterId") String submitterId,
            @RequestParam("approver") String approver,
            @RequestParam("approverId") String approverId,
            @RequestParam("editorContent") String editorContent,
            @RequestParam("attachments") MultipartFile[] attachments,
            @RequestParam("comment") String comment,
            RedirectAttributes redirectAttributes) {

    	logger.debug("subject :: {}", subject);					// 제목
    	logger.debug("submitter :: {}", submitter);				// 상신자
    	logger.debug("submitterId :: {}", submitterId);			// 상신자 사번
    	logger.debug("approver :: {}", approver);				// 결재자
    	logger.debug("approverId :: {}", approverId);			// 결재자 사번
    	logger.debug("editorContent :: {}", editorContent);		// 본문
    	logger.debug("comment :: {}", comment);					// 결재요청 코멘트
    	

        ApprDocVO apprDocVO = new ApprDocVO(); 
        
        String documentNo = UUID.randomUUID().toString();
        logger.debug("Generated documentId :: {}", documentNo);	// 문서번호
        
        long currentTime = System.currentTimeMillis();
        int randomNum = new Random().nextInt(1000);
        String fileId = null;
        
        for(MultipartFile file : attachments) {
    		if(!file.isEmpty()) {
    			fileId = String.format("%d%03d", currentTime, randomNum);
    	        apprDocVO.setFileId(fileId);
    		}
        }

        logger.debug("Generated fileId :: {}", fileId);			// 파일ID
        
        //결재문서저장 시작
        apprDocVO.setSubject(subject);
        apprDocVO.setSubmitter(submitter);
        apprDocVO.setSubmitterId(submitterId);
        apprDocVO.setApprover(approver);
        apprDocVO.setApproverId(approverId);
        apprDocVO.setContents(editorContent);
        apprDocVO.setComment(comment);
        apprDocVO.setResultStatus("submit");
        apprDocVO.setDocumentNo(documentNo);
        
        try {
            documentService.submit(apprDocVO);	
		} catch (Exception e) {
			logger.error("document submit error :: {}", e.getMessage());
			redirectAttributes.addAttribute("message", "제출 실패");
		}
        //결재문서저장 종료
        
        // 첨부파일 처리 시작
        String datePattern = "yyyyMMdd";
        String dateFolder = new SimpleDateFormat(datePattern).format(new Date());
        File uploadDir = new File(baseUploadDir, dateFolder);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // 폴더가 없으면 생성
            logger.debug("Created directory: {}", uploadDir.getAbsolutePath());
        }
        
        List<String> attachmentPaths = new ArrayList<>();
        try {
        	for(MultipartFile file : attachments) {
        		if(!file.isEmpty()) {
                    // 고유 파일 이름 설정 (fileId를 사용하여 파일명 포함)
                    String originalFilename = file.getOriginalFilename();
                    String newFilename = fileId + "_" + originalFilename;
                    File saveFile = new File(uploadDir, newFilename);

                    // 파일 저장
                    file.transferTo(saveFile);
                    
                    FileMetadata fileMetadata = new FileMetadata();
                    
                    fileMetadata.setFileId(fileId);
                    fileMetadata.setOriginalFilename(originalFilename);
                    fileMetadata.setStoredFilename(newFilename);
                    fileMetadata.setUploadDate(LocalDateTime.now());
                    fileMetadata.setSubmitterId(submitterId);
                    fileMetadata.setFilePath(saveFile.getAbsolutePath());

                    documentService.insertFile(fileMetadata);

                    // 저장된 파일 경로를 리스트에 추가
                    attachmentPaths.add(saveFile.getAbsolutePath());
                    logger.debug("attachments :: {}", file.getOriginalFilename());
        		}
        	}
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", "첨부파일 오류");
            logger.error("File upload error", e);
		}
    	// 첨부파일 처리 종료

        // 성공 후 리다이렉트
        redirectAttributes.addAttribute("result", "성공적으로 제출되었습니다.");

        return "redirect:/document.ex";
    }
    
}
