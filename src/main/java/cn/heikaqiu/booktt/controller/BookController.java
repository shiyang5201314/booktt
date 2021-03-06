package cn.heikaqiu.booktt.controller;

import cn.heikaqiu.booktt.bean.Book;
import cn.heikaqiu.booktt.bean.User;
import cn.heikaqiu.booktt.service.BookService;
import cn.heikaqiu.booktt.service.CollectionService;
import cn.heikaqiu.booktt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeiKaQiu
 * @create 2020-01-31 16:47
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CollectionService collectionService;

    /**
     * 获取当前书本类型id下的所有的书
     * @param id
     * @return
     */
    @GetMapping("/getBookByType/{id}")
    @ResponseBody
    public  Map<String, Object> getBookByType(@PathVariable("id") Integer id){
        List<Book> booksList=bookService.getBookByType(id,null,null);
        Map<String, Object> books=new HashMap<>();
        //System.out.println(booksList);
        //将查找到的list<book>封装
        books.put("books",booksList);

        return books;
    }


    /**
     * 分页获取不同书本类型的书
     * @param type_id
     * @param index_num
     * @param page_num
     * @return
     */
    @GetMapping("/getBookByType")
    @ResponseBody
    public  Map<String, Object> getBookByTypeLimit(Integer type_id,Integer index_num,Integer page_num){

        //System.out.println(type_id+"++"+index_num+"page_num"+page_num);
        //分页查询此类型书本
        List<Book> booksList=bookService.getBookByType(type_id,index_num,page_num);
        Map<String, Object> books=new HashMap<>();
        //System.out.println(booksList);
        //将查找到的list<book>封装
        books.put("books",booksList);


        return books;
    }

    /**
     * 查询此书本类型下有多少本书
     */
    @GetMapping("/getBookNumByType/{id}")
    @ResponseBody
    public Map<String , Integer> getBookNumByType(@PathVariable("id") Integer id){

        Integer bookNumByType=bookService.getBookNumByType(id);
        Map<String ,Integer> num=new HashMap<>();
        num.put("num",bookNumByType);
        return num;
    }


    /**
     * 图书详细页面
     * @param id
     * @return
     */
    @GetMapping("/BookInfo/{id}")
    public String BookInfo(@PathVariable("id") Integer id, Model model){




        session.setAttribute("page", "/");

        Book book=bookService.getBookInfoById(id);
        model.addAttribute("book",book);
        System.out.println(book);


        //查找书的平均星
        Float AverageStar=bookService.getAverageStarByBookId(id);
        if(AverageStar==null){
            //没人给他评论给过
            model.addAttribute("AverageStar", "没人参与评论");
        }else{
            String AverageStarString = String.format("%.1f", AverageStar);
            model.addAttribute("AverageStar", AverageStarString);
        }



        //查看用户是否已经收藏
        User login_user = (User) session.getAttribute("login_user");
        if(login_user==null){
            //不用去找
            model.addAttribute("isCollection", false);
        }else{
            //有用户登录 就去找
            boolean isCollection=collectionService.isCollection(login_user.getId(),book.getId());
            model.addAttribute("isCollection", isCollection);
            System.out.println(isCollection);

        }

        return "BookInfo";
    }




}
