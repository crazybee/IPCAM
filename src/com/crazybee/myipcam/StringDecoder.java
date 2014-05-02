package com.crazybee.myipcam;

public class StringDecoder {
	
   public static String getName(String input, int para){
	   String[] sourceStrArray=input.split("&");
	   for(int i=0;i<sourceStrArray.length;i++)
	   {
		   if (sourceStrArray[0].equals("cr"))
	    {
			   System.out.println(sourceStrArray[i]);
			 
	    
	    } else {
	    	System.out.println("not correct string");
	        return "not correct";}
		   
		   switch (para){
		   case 1:
			   return sourceStrArray[1];
			 
		   case 2:
			   return sourceStrArray[2];
		   case 3:
			   return sourceStrArray[3];
		   case 4:
			   return sourceStrArray[4];
			   
		   
		   
		   }
	    }
	   
	   
	   
	   
	   
	   
		   return "not correct";
	   }
	   
	   
	  
   public static String getPassword(){return "";}
   public static String ip(){return "";}
   public static String port(){return "";}
   
}
