package org.woodee.controller;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.woodee.domain.BoardVO;
import org.woodee.domain.Criteria;
import org.woodee.domain.PageDTO;
import org.woodee.service.BoardService;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    private BoardService service;

    //페이징 처리 없는 조회
    /*@GetMapping("/list")
    public void list(Model model){
        log.info("list");
        model.addAttribute("list",service.getList());
    }*/

    @GetMapping("/list")
    public void getList(Criteria cri, Model model) {
        log.info("list" + cri);
        model.addAttribute("list", service.getList(cri));
        //현재 total 에 대한 로직이 없기 때문에 임시의 값 123 설정
        //생각해보니 pageNo와 amount가 1,10으로 초기화한 Criteria를 PageDTO의 파라미터로 받고 생성해서
        //Model 에 담아서 View 로 전달해서 /board/list?pageNum에 있는게 아니라
        //그냥 list 인데 내가 페이징을 누를 때 파라미터를 보내나보다.
        //결론: hidden 타입으로 form 안에 있는 pageNum, amount 를 파라미터로 보내기 때문이다.
        model.addAttribute("pageMaker", new PageDTO(cri, 123 ));
    }


    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes redirectAttributes){
        log.info("register : " + board);
        service.register(board);
        redirectAttributes.addFlashAttribute("result", board.getBno());
        return "redirect:/board/list";
    }

    @GetMapping({"/get","/modify"})
    public void get(@RequestParam("bno") Long bno, Model model){
        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, RedirectAttributes redirectAttributes){
        log.info("modify : " + board);
        if(service.modify(board)){
            redirectAttributes.addFlashAttribute("result", "success");
        }

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes redirectAttributes){
        log.info("remove : " + bno);
        if(service.remove(bno)){
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";

    }

}
