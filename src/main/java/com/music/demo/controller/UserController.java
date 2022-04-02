package com.music.demo.controller;

import com.music.demo.pojo.comment.*;
import com.music.demo.pojo.user.*;
import com.music.demo.service.MusicService;
import com.music.demo.uitls.UploadImgutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    MusicService service;

    //注册
    @PostMapping("/register")
    public RegisterStatusAndLoginStatus RegisterUser(@RequestParam String UserAccount, @RequestParam String UserPassword, @RequestParam String UserName, @RequestParam String Icon) {
        UserBaseInfo bean = new UserBaseInfo();
        bean.setUaccount(UserAccount);
        bean.setUpassword(UserPassword);
        bean.setUser_name(UserName);
        bean.setUser_avatarUrl("http://localhost:8081/viewPhoto/" + Icon);
        System.out.println(bean.toString());
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        Map<Object, Object> RegisterMap = new HashMap<>();
        RegisterMap.put("username", UserAccount);
        RegisterMap.put("password", UserPassword);
        List<UserBaseInfo> getResultList = new ArrayList<>();
        List<UserBaseInfo> getResultList2 = new ArrayList<>();
        getResultList = service.Select_isRegister(RegisterMap);
        if (UserAccount != null && UserPassword != null && UserName != null && Icon != null) {
            if (getResultList.size() == 1) {
                StatusBean.setStatus(false);
                StatusBean.setCode("400");
                StatusBean.setContent("已经注册过");
            } else if (service.RegisterUser(bean) > 0) {
                Map<Object, Object> CreateMap = new HashMap<>();
                CreateMap.put("username", UserAccount);
                CreateMap.put("password", UserPassword);
                getResultList2 = service.Select_isRegister(CreateMap);
                long tempId = getResultList2.get(0).getUid();
                StatusBean.setStatus(true);
                StatusBean.setCode("200");
                StatusBean.setContent("注册成功");
                DefaultCreatePlaylist(tempId);
            }
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("500");
            StatusBean.setContent("数据错误");
        }
        return StatusBean;
    }

    //登录
    @PostMapping("/login")
    public RegisterStatusAndLoginStatus Login(@RequestParam String Account, @RequestParam String Password) {
        System.out.println(Account);
        System.out.println(Password);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        Map<Object, Object> loginMap = new HashMap<>();
        loginMap.put("username", Account);
        loginMap.put("password", Password);
        List<UserBaseInfo> list = service.Login(loginMap);
        if (list.size() > 0) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("登录成功");
            StatusBean.setRequestBody(list.get(0));
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("登录失败");
            StatusBean.setRequestBody(null);
        }
        return StatusBean;
    }

    //信息修改
    @PostMapping("/infoUpdate")
    public RegisterStatusAndLoginStatus InformationUpdate(@RequestParam long uid, @RequestParam String User_name, @RequestParam String User_avatarUrl, @RequestParam String User_introduction, @RequestParam String User_gender, @RequestParam String User_birthday, @RequestParam String User_address) {
        UpdateUserInfo bean = new UpdateUserInfo();
        bean.setUid(uid);
        bean.setUser_name(User_name);
        if (User_avatarUrl.equals("无")) {
            bean.setUser_avatarUrl(null);
        } else {
            bean.setUser_avatarUrl("http://localhost:8081/viewPhoto/" + User_avatarUrl);
        }
        bean.setUser_introduction(User_introduction);
        bean.setUser_gender(User_gender);
        bean.setUser_birthday(User_birthday);
        bean.setUser_address(User_address);
        System.out.println(bean.toString());
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.UpdateInfo(bean) == 1) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("修改成功");
            StatusBean.setRequestBody(service.getUserIdreInfo(bean.getUid()).get(0));
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("修改失败");
        }
        return StatusBean;
    }

    //根据用户id查询用户的信息
    @GetMapping("/gerUserinfo/{uid}")
    @ResponseBody
    public UserBaseInfo getUserinfo(@PathVariable String uid){
        return service.getUserIdreInfo(Long.parseLong(uid)).get(0);
    }

    //查询用户创建的歌单
    @GetMapping("/userCplaylist/{id}")
    @ResponseBody
    public List<CreatePlaylist> SelectUserPlaylist(@PathVariable long id) {
        System.out.println(id);
        List<CreatePlaylist> list = new ArrayList<>();
        list = service.SelectUserCplaylist(id);
        System.out.println(list);
        return list;
    }

    //添加歌曲到歌单
    @PostMapping("/addSongToPlaylist")
    public RegisterStatusAndLoginStatus AddSongToPlaylist(@RequestParam String Music_id, @RequestParam String Music_name, @RequestParam String Music_album_name, @RequestParam String Music_singer_name, @RequestParam String Music_album_id, @RequestParam String Music_singer_id, @RequestParam String playlist_id) {
        AddSongBean bean = new AddSongBean();
        bean.setMusic_id(Music_id);
        bean.setMusic_name(Music_name);
        bean.setMusic_album_name(Music_album_name);
        bean.setMusic_album_id(Music_album_id);
        bean.setMusic_singer_name(Music_singer_name);
        bean.setMusic_singer_id(Music_singer_id);
        bean.setPlaylist_id(playlist_id);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.PlaylistHasThisSong(Integer.parseInt(bean.getPlaylist_id()), Integer.parseInt(bean.getMusic_id()))) {
            if (service.AddMyLikeSong(bean) > 0) {
                StatusBean.setStatus(true);
                StatusBean.setCode("200");
                StatusBean.setContent("添加成功");
                StatusBean.setRequestBody("歌曲已经成功添加到对应歌单");
            } else {
                StatusBean.setStatus(false);
                StatusBean.setCode("500");
                StatusBean.setContent("添加失败");
                StatusBean.setRequestBody("添加到对应歌单失败");
            }
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("501");
            StatusBean.setContent("添加失败");
            StatusBean.setRequestBody("歌曲已经存在");
        }

        return StatusBean;
    }

    private void DefaultCreatePlaylist(long id) {
        CreatePlaylist bean = new CreatePlaylist();
        bean.setPlaylist_icon("https://y.qq.com/mediastyle/y/img/cover_love_300.jpg?max_age=2592000");
        bean.setPlaylist_name("我喜欢的音乐");
        bean.setUser_id(id);
        service.InsertDefaultPlaylist(bean);
    }

    //查询用户创建的歌单
    @GetMapping("/userPlaylist/{id}")
    @ResponseBody
    public List<CreatePlaylist> SelectUserPlaylistForPlaylistId(@PathVariable long id) {
        List<CreatePlaylist> list = service.getIdSelectUserPlaylist(id);
        return list;
    }

    //根据用户歌单id查询属于歌单的歌曲
    //查询用户创建的歌单
    @GetMapping("/userPlaylistSong/{id}")
    @ResponseBody
    public List<AddSongBean> SelectSongForPLaylistId(@PathVariable long id) {
        List<AddSongBean> list = service.getSongForPlaylistID(id);
        return list;
    }

    @PostMapping("/upload")
    // @RequestParam中的file名应与前端的值保持一致
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        // replaceAll 用来替换windows中的\\ 为 /
        System.out.println("访问");
        return UploadImgutil.upload(multipartFile).replaceAll("\\\\", "/");
    }

    @RequestMapping("/viewPhoto/{photopath}")
    public void getFeedBackPicture(HttpServletResponse response, @PathVariable("photopath") String photopath) throws Exception {
        String realPath = "F:/JetBrains/Java/WebMusic_project/src/main/resources/static/publicImg/" + photopath;
        FileInputStream inputStream = new FileInputStream(realPath);
        int i = inputStream.available();
        //byte数组用于存放图片字节数据
        byte[] buff = new byte[i];
        inputStream.read(buff);
        //记得关闭输入流
        inputStream.close();
        //设置发送到客户端的响应内容类型
        response.setContentType("image/*");
        OutputStream out = response.getOutputStream();
        out.write(buff);
        //关闭响应输出流
        out.close();
    }

    @GetMapping("/DeleteUserPlaylist/{id}")
    @ResponseBody
    public RegisterStatusAndLoginStatus DeleteUserPlaylist(@PathVariable long id) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        System.out.println(id);
        if (service.DeleteUserPlaylist(id) == 1) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("删除成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("删除失败");
        }
        return StatusBean;
    }

    @PostMapping("/UserPlaylistCreate")
    public RegisterStatusAndLoginStatus CreateUserPlaylist(@RequestParam String Playlist_name, @RequestParam String Playlist_desc, @RequestParam String Playlist_tags, @RequestParam String Playlist_icon, @RequestParam long User_id) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        CreatePlaylist createBean = new CreatePlaylist();
        createBean.setPlaylist_name(Playlist_name);
        createBean.setPlaylist_desc(Playlist_desc);
        createBean.setPlaylist_tags(Playlist_tags);
        createBean.setPlaylist_icon("http://localhost:8081/viewPhoto/" + Playlist_icon);
        createBean.setUser_id(User_id);
        System.out.println(createBean.toString());
        if (service.CreateUserPlaylist(createBean) == 1) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("创建成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("创建失败");
        }
        return StatusBean;
    }

    @GetMapping("/DeleteUserSong/{PlaylistID}/{MusicID}")
    @ResponseBody
    public RegisterStatusAndLoginStatus DeleteUserSong(@PathVariable long PlaylistID, @PathVariable long MusicID) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.DeleteUserPlaylistSong(PlaylistID, MusicID) == 1) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("删除成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("删除失败");
        }
        return StatusBean;
    }

    //收藏歌单
    @GetMapping("/collect/playlist/{pid}/{pname}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UserCollectPlaylist(@PathVariable long pid, @PathVariable String pname, @PathVariable long uid) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.CollectPlaylist(pid, pname, uid)) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("收藏成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("收藏失败");
        }
        return StatusBean;
    }

    //收藏歌曲
    @GetMapping("/collect/song/{sid}/{sname}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UserCollectSong(@PathVariable long sid, @PathVariable String sname, @PathVariable long uid) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.CollectSong(sid, sname, uid)) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("收藏成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("收藏失败");
        }
        return StatusBean;
    }

    //收藏歌手
    @GetMapping("/collect/singer/{sinid}/{sinname}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UserCollectSinger(@PathVariable long sinid, @PathVariable String sinname, @PathVariable long uid) {
        System.out.println(sinid+"----"+uid+"----"+sinname);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.CollectSinger(sinid, sinname, uid)) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("收藏成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("收藏失败");
        }
        return StatusBean;
    }

    //收藏MV
    @GetMapping("/collect/vediomv/{mvid}/{mvname}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UserCollectMv(@PathVariable long mvid, @PathVariable String mvname, @PathVariable long uid) {
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if (service.CollectMV(mvid, mvname, uid)) {
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("收藏成功");
        } else {
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("收藏失败");
        }
        return StatusBean;
    }

    //查询用户收藏的歌单
    @GetMapping("/ucPlaylist/{uid}")
    @ResponseBody
    public List<CollectPlaylist> SelectUCPlaylist(@PathVariable long uid) {
        List<CollectPlaylist> list = service.SelectUserCollectPlaylist(uid);
        return list;
    }

    //查询用户收藏的歌曲
    @GetMapping("/ucSong/{uid}")
    @ResponseBody
    public List<CollectSong> SelectUCSong(@PathVariable long uid) {
        List<CollectSong> list = service.SelectUserCollectSong(uid);
        return list;
    }

    //查询用户收藏的歌手
    @GetMapping("/ucSinger/{uid}")
    @ResponseBody
    public List<CollectSinger> SelectUCSinger(@PathVariable long uid) {
        List<CollectSinger> list = service.SelectUserCollectSinger(uid);
        return list;
    }

    //查询用户收藏的mv
    @GetMapping("/ucMv/{uid}")
    @ResponseBody
    public List<CollectMv> SelectUCMv(@PathVariable long uid) {
        List<CollectMv> list = service.SelectUserCollectMv(uid);
        return list;
    }

    //根据歌单id 和用户id查询该用户是否已经收藏过改歌单
    @GetMapping("/hasCPlaylist/{pid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus HasCPlaylist(@PathVariable long pid,@PathVariable long uid){
        System.out.println(pid);
        System.out.println(uid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.HasCollectPlaylist(pid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("没有收藏过");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("已经收藏过");
        }
        return StatusBean;
    }

    //根据歌曲id 和用户id查询该用户是否已经收藏过改歌曲
    @GetMapping("/hasCSong/{sid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus HasCSong(@PathVariable long sid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.HasCollectSong(sid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("没有收藏过");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("已经收藏过");
        }
        return StatusBean;
    }

    //根据歌手id 和用户id查询该用户是否已经收藏过改歌手
    @GetMapping("/hasCSinger/{sinid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus HasCSinger(@PathVariable long sinid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.HasCollectSinger(sinid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("没有收藏过");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("已经收藏过");
        }
        return StatusBean;
    }

    //根据Mvid 和用户id查询该用户是否已经收藏过改Mv
    @GetMapping("/hasCMv/{mvid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus HasCMv(@PathVariable long mvid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.HasCollectMv(mvid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("没有收藏过");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("已经收藏过");
        }
        return StatusBean;
    }

    //取消收藏歌单
    @GetMapping("UnCPlaylist/{pid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UnCPlaylist(@PathVariable long pid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.UnCollectPlaylist(pid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("成功取消收藏");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("取消收藏失败");
        }
        return StatusBean;
    }

    //取消收藏歌曲
    @GetMapping("UnCSong/{sid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UnCSong(@PathVariable long sid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.UnCollectSong(sid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("成功取消收藏");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("取消收藏失败");
        }
        return StatusBean;
    }

    //取消收藏歌手
    @GetMapping("UnCSinger/{sinid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UnCSinger(@PathVariable long sinid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.UnCollectSinger(sinid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("成功取消收藏");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("取消收藏失败");
        }
        return StatusBean;
    }

    //取消收藏MV
    @GetMapping("UnCMv/{mvid}/{uid}")
    @ResponseBody
    public RegisterStatusAndLoginStatus UnCMv(@PathVariable long mvid,@PathVariable long uid){
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.UnCollectMv(mvid, uid)){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("成功取消收藏");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("取消收藏失败");
        }
        return StatusBean;
    }

    //通过mvid查询这个mv的所有评论
    @GetMapping("/MvComment/all/{mvid}")
    @ResponseBody
    public List<mvcomment> MvAllComment(@PathVariable String mvid){
        List<mvcomment> list = service.SelectMVComment(Long.parseLong(mvid));
        return list;
    }

    //通过songid查询这个歌曲的所有评论
    @GetMapping("/SongComment/all/{songid}")
    @ResponseBody
    public List<SongComment> SongAllComment(@PathVariable String songid){
        List<SongComment> list = service.SelectSongComment(Long.parseLong(songid));
        return list;
    }

    //通过playlist id查询这个歌单的所有评论
    @GetMapping("/PlaylistComment/all/{playlistid}")
    @ResponseBody
    public List<PlaylistComment> PlaylistAllComment(@PathVariable String playlistid){
        List<PlaylistComment> list = service.SelectPlaylistComment(Long.parseLong(playlistid));
        return list;
    }

    //根据用户id查询这个用户的所有动态
    @GetMapping("/UserComment/all/{uid}")
    @ResponseBody
    public List<UserComment> UserAllComment(@PathVariable int uid){
        List<UserComment> list = service.SelectUserComment(uid);
        return list;
    }

    //根据动态id 吧用户动态的一级 评论查询出来
    @GetMapping("/firstDt/all/{dtid}")
    @ResponseBody
    List<ReplayComment> SelectUserDTFirst(@PathVariable int dtid){
        List<ReplayComment> list = service.SelectUserDTFirstComment(dtid);
        return list;
    }

    //根据reply回复表的动态id news_id 当本表的id 与这个动态id一致时就是这个动态一级评论的回复内容
    @GetMapping("/secondDt/all/{dtid}")
    @ResponseBody
    List<ReplayComment> SelectUserDTSecond(@PathVariable int dtid){
        List<ReplayComment> list = service.SelectUserDTSecondComment(dtid);
        return list;
    }

    //根据mv的一级评论id 返回mv所对应的评论回复
    @GetMapping("/replyMV/all/{dtid}")
    @ResponseBody
    List<ReplayComment> replyMV(@PathVariable int dtid){
        List<ReplayComment> list = service.MvReply(dtid);
        return list;
    }

    @GetMapping("/replySong/all/{dtid}")
    @ResponseBody
    List<ReplayComment> replySong(@PathVariable int dtid){
        List<ReplayComment> list = service.SongReply(dtid);
        return list;
    }

    @GetMapping("/replyPlaylist/all/{dtid}")
    @ResponseBody
    List<ReplayComment> replyPlaylist(@PathVariable int dtid){
        List<ReplayComment> list = service.PlaylistReply(dtid);
        return list;
    }

    //给mv的评论进行回复
    @PostMapping("/mvReply")
    public RegisterStatusAndLoginStatus mvReplys(@RequestParam long mvid,@RequestParam String content,@RequestParam int replyUserid){
        System.out.println(mvid);
        System.out.println(content);
        System.out.println(replyUserid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.Replay_mv(content,replyUserid,mvid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论mv成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论mv失败");
        }
        return StatusBean;
    }

    //给song的评论进行回复
    @PostMapping("/songReply")
    public RegisterStatusAndLoginStatus songReplys(@RequestParam long songid,@RequestParam String content,@RequestParam int replyUserid){
        System.out.println(songid);
        System.out.println(content);
        System.out.println(replyUserid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.Replay_song(content,replyUserid,songid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论song成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论song失败");
        }
        return StatusBean;
    }

    //给playlist的评论进行回复
    @PostMapping("/playlistReply")
    public RegisterStatusAndLoginStatus playlistReplys(@RequestParam long playlistid,@RequestParam String content,@RequestParam int replyUserid){
        System.out.println(playlistid);
        System.out.println(content);
        System.out.println(replyUserid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.Replay_playlist(content,replyUserid,playlistid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论playlist成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论playlist失败");
        }
        return StatusBean;
    }

    //对动态的一级评论进行回复 根据一级动态评论的 评论id
    @PostMapping("/userReply")
    public RegisterStatusAndLoginStatus userReply(@RequestParam int DTid,@RequestParam String content,@RequestParam int replyUserid,@RequestParam String time){
        System.out.println(DTid);
        System.out.println(content);
        System.out.println(replyUserid);
        System.out.println(time);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.ReplyFirstDT(DTid,content,replyUserid,time)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论用户成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论用户失败");
        }
        return StatusBean;
    }

    @PostMapping("/cMv")
    public RegisterStatusAndLoginStatus cMV(@RequestParam String content,@RequestParam String time,@RequestParam long uid,@RequestParam long mvid){
        System.out.println(content);
        System.out.println(time);
        System.out.println(uid);
        System.out.println(mvid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.CommentThisMv(content, time, uid, mvid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论mv成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论mv失败");
        }
        return StatusBean;
    }

    @PostMapping("/cSong")
    public RegisterStatusAndLoginStatus cSong(@RequestParam String content,@RequestParam String time,@RequestParam long uid,@RequestParam long songid){
        System.out.println(content);
        System.out.println(time);
        System.out.println(uid);
        System.out.println(songid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.CommentThisSong(content, time, uid, songid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论mv成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论mv失败");
        }
        return StatusBean;
    }

    @PostMapping("/cPlaylist")
    public RegisterStatusAndLoginStatus cPlaylist(@RequestParam String content,@RequestParam String time,@RequestParam long uid,@RequestParam long playlistid){
        System.out.println(content);
        System.out.println(time);
        System.out.println(uid);
        System.out.println(playlistid);
        RegisterStatusAndLoginStatus StatusBean = new RegisterStatusAndLoginStatus();
        if(service.CommentThisPlaylist(content, time, uid, playlistid)>0){
            StatusBean.setStatus(true);
            StatusBean.setCode("200");
            StatusBean.setContent("评论mv成功");
        }else{
            StatusBean.setStatus(false);
            StatusBean.setCode("400");
            StatusBean.setContent("评论mv失败");
        }
        return StatusBean;
    }

}
