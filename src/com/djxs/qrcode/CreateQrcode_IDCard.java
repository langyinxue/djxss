package com.djxs.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.swetake.util.Qrcode;

public class CreateQrcode_IDCard {

	
	public static void main(String[] args) throws IOException {

		
		Qrcode qrcode=new Qrcode();
		qrcode.setQrcodeEncodeMode('L');
		qrcode.setQrcodeErrorCorrect('Q');
		//qrcode.setQrcodeVersion(ver);//���ð汾��
		
        String str="BEGIN:VCARD\n"+
                   "PHOTO;VALUE=uir:http://p2.so.qhmsg.com/bdr/_240_/t018a3ada763270c227.jpg\n" +
                   "FN:����:��ӡѩ\n"+
                   "TITLE:�ӱ��Ƽ�ʦ��ѧԺѧ��\n"+
                   "ADR;WORK:;;�ػʵ�������������ӱ��������360��\n"+
                   "TEL;CELL,VOICE��18232930304\n"+
                   "TEL;WORK,VOICE:��\r\n"+
                   "URL;WORK:http://www.hevttc.edu.cn\n"+
                   "EMAIL;INTERNET,HOME:1696509339@qq.com\n"+
                   "END:VCARD";//��ά��ɨ����������
        //�õ�һ����ά����
        boolean[][] calQrcode=qrcode.calQrcode(str.getBytes("UTF-8"));
        /*��ά���С�����ұ�׼��
		 * ver = 1 , imagesize=21
		 * ver = 2 , imagesize=25
		 * ver = 3 , imagesize=29
		 * ver = n , imagesize=21+(ver-1)*4
		 * ��ÿһλ��mλ���ص��ʾʱ��Ĭ��һλ����
		 * imagesize=(21+(ver-1)*4)*m
		 * ���ı߼�x�����صİױ�ʱ����Ĭ�ϲ��ӣ���
		 *imagesize=((21+x*2)+(ver-1)*4)*m
		 */
		int x=2;//������������صİױ�
		int imagesize=67+12*(qrcode.getQrcodeVersion()-1);//��ά���С
		
		BufferedImage bufferedimage=new BufferedImage(imagesize,imagesize,BufferedImage.TYPE_INT_RGB);//���ö�ά���С
		Graphics2D gs1=bufferedimage.createGraphics();
		gs1.setBackground(Color.WHITE);
		gs1.setColor(Color.BLACK);
		gs1.clearRect(0,0,imagesize,imagesize);//�������bufferedimage�Ĵ�Сһ������Ȼ���кڱ�
		
        int startR=231;
        int startG=89;
        int startB=25;
        
        int endR=22;
        int endG=234;
        int endB=108;
        
        for (int i=0;i<calQrcode.length;i++){
        	for (int j=0;j<calQrcode.length;j++){
        		if(calQrcode[i][j]){
        		   /*x=��ʼֵ+������ֵ-��ʼֵ��*����/����
        			*                   j+1       ������       ���䣩
        			*                   i+1       ������       ���䣩
        			*                  (i+j)/2�����Խ��߽��䣩
        			*/ 
        		
        		   int num1=startR+(endR-startR)*((i+j)/2)/calQrcode.length;
        		   int num2=startG+(endG-startG)*((i+j)/2)/calQrcode.length;
        		   int num3=startB+(endB-startB)*((i+j)/2)/calQrcode.length;
        		
        		   Color color1= new Color(num1,num2,num3);
        		   gs1.setColor(color1);
        		   gs1.fillRect(i*3+x,j*3+x,3,3);//(i*m+x,j*m+x,m,m)
        		  
        		}
        	}
        }
        Image logo=ImageIO.read(new File("D:/lyxid.jpg"));
        int logosize=50;
        int logox=(imagesize-logosize)/2;
        gs1.drawImage(logo,logox, logox, logosize, logosize, null);
        gs1.dispose();
    	bufferedimage.flush();
    	ImageIO.write(bufferedimage,"png",new File("D:/��ά������.png"));
	}
}