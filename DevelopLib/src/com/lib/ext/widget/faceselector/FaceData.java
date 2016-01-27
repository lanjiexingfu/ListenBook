package com.lib.ext.widget.faceselector;

import java.util.ArrayList;
import java.util.List;

import com.lib.R;

public class FaceData {
	private static int[] faceId={R.drawable.f_static_000,R.drawable.f_static_001,R.drawable.f_static_002,R.drawable.f_static_003
        ,R.drawable.f_static_004,R.drawable.f_static_005,R.drawable.f_static_006,R.drawable.f_static_009,R.drawable.f_static_010,R.drawable.f_static_011
        ,R.drawable.f_static_012,R.drawable.f_static_013,R.drawable.f_static_014,R.drawable.f_static_015,R.drawable.f_static_017,R.drawable.f_static_018};
	private static String[] faceName={"\\呲牙","\\淘气","\\流汗","\\偷笑","\\再见","\\敲打","\\擦汗","\\流泪","\\掉泪","\\小声","\\炫酷","\\发狂"
         ,"\\委屈","\\便便","\\菜刀","\\微笑","\\色色","\\害羞"};
	
	private static List<Face> faceData = new ArrayList<Face>();
	
	static {
		faceData.clear();
		for(int i = 0;i<faceId.length;i++){
			Face face = new Face();
			face.setFaceRes(faceId[i]);
			face.setFaceName(faceName[i]);
			face.setPosition(i);
			faceData.add(face);
		}
	}
	
	/**
	 * 获取某个表情
	 * @param index
	 * @return
	 */
	public static Face get(int index){
		return faceData.get(index);
	}
	
	/**
	 * 返回表情的数量
	 * @return
	 */
	public static int size(){
		return faceData.size();
	}
	
	
}
