package com.suyash.resumeanalyzer.controller;

import com.suyash.resumeanalyzer.model.Student;
import com.suyash.resumeanalyzer.service.StudentService;
import com.suyash.resumeanalyzer.service.AIService;
import com.suyash.resumeanalyzer.model.User;
import com.suyash.resumeanalyzer.service.JwtService;
import com.suyash.resumeanalyzer.service.UserService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HelloController
{
    private final StudentService service;
    private final AIService aiService;
    private final UserService userService;
    private final JwtService jwtService;
    public HelloController(
            StudentService service,
              UserService userService,
             JwtService jwtService,
            AIService aiService)
    {
        this.service = service;
        this.aiService = aiService;
        this.jwtService=jwtService;
        this.userService = userService;
    }

    @PostMapping("/student")
    public Student createStudent(
            @RequestBody Student student)
    {
        return service.saveStudent(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents()
    {
        return service.getAllStudents();
    }

    @DeleteMapping("/student/{id}")
    public String deleteStudent(
            @PathVariable Long id)
    {
        service.deleteStudent(id);

        return "Student deleted 😎";
    }

    @PutMapping("/student/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody Student student)
    {
        return service.updateStudent(id, student);
    }
@PostMapping("/signup")
public User signup(
        @RequestBody User user)
{
    return userService.signup(user);
}
    @PostMapping("/upload")
    public String uploadResume(
            @RequestParam("file")
            MultipartFile file) throws Exception
    {
        PDDocument document =
                PDDocument.load(file.getInputStream());

        PDFTextStripper pdfTextStripper =
                new PDFTextStripper();

        String text =
                pdfTextStripper.getText(document);

        document.close();

        return aiService.analyzeResume(text);
    }
    @PostMapping("/login")
    public HttpEntity<Map<String, String>> login(
            @RequestBody User user)
    {
        return userService.login(user);
    }
}