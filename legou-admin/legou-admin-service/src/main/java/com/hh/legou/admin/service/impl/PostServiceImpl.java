package com.hh.legou.admin.service.impl;

import com.hh.legou.admin.po.Post;
import com.hh.legou.admin.service.IPostService;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends CrudServiceImpl<Post> implements IPostService {

}
