package cn.henau.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.constants.Constants;
import cn.henau.cms.dto.TagDTO;
import cn.xsshome.taip.vision.TAipVision;

@Component
public class TencentAiService {

	public List<TagDTO> visionPornImg(String filePath) {
		TAipVision aipVision = new TAipVision(Constants.TENCENT_APP_ID, Constants.TENCENT_APP_KEY);
        String res = "";
        try {
            res = aipVision.visionPorn(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        int first = res.indexOf("[");
        int last = res.lastIndexOf("]");
        String listString = res.substring(first + 1, last);
        String[] tags = listString.split("},");
        List<TagDTO> tagList = new ArrayList<>();
        int i=1;
        for (String tag : tags) {
            if (i<10)
                tag = tag + "}";
            i++;
            TagDTO tagDTO = gson.fromJson(tag, TagDTO.class);
            tagList.add(tagDTO);
        }
        return tagList;
	}
	
	public String parseData(List<TagDTO> tagList) {
		int confidence=0;
		for(TagDTO tag:tagList) {
			if(tag.getTag_name().equals("female-breast")) {
				confidence=tag.getTag_confidence();
				if(confidence>60) {
					return "图片过于性感，检测到“胸部”，建议更换图片。";
				}
			}
		}
		for(TagDTO tag:tagList) {
			if(tag.getTag_name().equals("hot")) {
				confidence=tag.getTag_confidence();
				if(confidence>60) {
					return "图片过于性感，建议更换图片。";
				}
			}
		}
		for(TagDTO tag:tagList) {
			if(tag.getTag_name().equals("porn")) {
				confidence=tag.getTag_confidence();
				if(confidence>60) {
					return "图片含有色情成分，请立即更换图片，否则将面临封号的惩罚！";
				}
			}
		}
		return "正常";
	}
	
}
