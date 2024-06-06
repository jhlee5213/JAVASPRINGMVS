package org.example.member03.controller;

import org.example.member03.domain.Member;
import org.example.member03.domain.MemberForm;
import org.example.member03.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController() {
        memberService = new MemberService();
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String join(@ModelAttribute MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        return "redirect:/members";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/list";
    }
}
