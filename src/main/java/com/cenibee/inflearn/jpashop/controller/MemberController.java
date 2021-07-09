package com.cenibee.inflearn.jpashop.controller;

import com.cenibee.inflearn.jpashop.domain.Address;
import com.cenibee.inflearn.jpashop.domain.Member;
import com.cenibee.inflearn.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setUsername(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
