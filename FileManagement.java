package Project;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

public class FileManagement {
	public static String getLinkFolder() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String path_folder = "";
		File folder;
		do {
			System.out.println("Mời bạn nhập đường dẫn thư mục: ");
			path_folder = sc.nextLine();
			folder = new File(path_folder);
			if( !(folder.exists() && folder.isDirectory()) ){
				System.err.println("Đường dẫn không hợp lệ !!! Vui lòng nhập lại....");
			}
		}while( !(folder.exists() && folder.isDirectory()) );
		return path_folder;	
	}
	public static void searchByFileName() {
		Scanner sc = new Scanner(System.in);
		String link = getLinkFolder();
		String keyword;
		File[] list = new File(link).listFiles();
		System.out.println("Folder hiện tại: " + link);
		System.out.println("Tổng số file: " + list.length);
		System.out.print("Nhập từ khóa tìm kiếm: ");
		keyword = sc.nextLine();
		System.out.println("----------------------------------------------------------------------------THÔNG TIN CHI TIẾT-----------------------------------------------------------------------------");
		System.out.println("+            Tên File                +    				       Đường dẫn                           +     Kích thước(KB)  +         Cập nhật lần cuối       +");
		System.out.println("+------------------------------------+-----------------------------------------------------------------------------+---------------------+---------------------------------+");
		for(int i = 0; i < list.length; ++i) {
			if(list[i].getName().contains(keyword)) {
				long time = list[i].lastModified();
				Date last_update = new Date(time);
				System.out.printf("|%36s|%77s|%21s|%33s|\n", list[i].getName(), list[i].getAbsolutePath(), list[i].length()/1024, last_update);
				System.out.println("+------------------------------------+-----------------------------------------------------------------------------+---------------------+---------------------------------+");			}
		}
		System.out.println("\n");
	}
	public static String getFileExtension(File file_name) {
		String name = file_name.getName();
		int pos = name.lastIndexOf('.');
		if(pos != -1) {
			return name.substring(pos + 1);
		}
		else return "";
	}
	public static void renameAllFiles() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String link = getLinkFolder();
		String prefix;
		File[] list = new File(link).listFiles();
		System.out.println("Folder hiện tại: " + link);
		System.out.println("Tổng số file: " + list.length);
		System.out.print("Nhập tiền tố: ");
		prefix = sc.nextLine();
		int counter = 1;
		for(File file : list) {
			if(file.isFile()) {
				file.renameTo(new File(link + "\\" + prefix + "_" + counter + "." + getFileExtension(file)));
				counter++;
			}
		}
		System.out.println("\nĐã đổi tên thành công!");
	}
	public static void cleanFolder() throws IOException {
		String link = getLinkFolder();
		File[] list = new File(link).listFiles();
		System.out.println("Folder hiện tại: " + link);
		
		for(File file : list) {
			if(file.isFile()) {
				if(getFileExtension(file).equals("pdf") || getFileExtension(file).equals("docx") || getFileExtension(file).equals("xlsx") || getFileExtension(file).equals("pptx"))
				{
					File fileDocument = new File(link + "\\Documents");
					if(!fileDocument.exists()) {
						fileDocument.mkdir();
					}
					Files.move(Paths.get(file.getPath()), Paths.get(fileDocument.getPath() + "\\" + file.getName()));
				}
				else if(getFileExtension(file).equals("jpg") || getFileExtension(file).equals("png"))
				{
					File fileImage = new File(link + "\\Images");
					if(!fileImage.exists()) {
						fileImage.mkdir();
					}
					Files.move(Paths.get(file.getPath()), Paths.get(fileImage.getPath() + "\\" + file.getName()));
				}
				else if(getFileExtension(file).equals("wav") || getFileExtension(file).equals("mp3"))
				{
					File fileAudio = new File(link + "\\Audio");
					if(!fileAudio.exists()) {
						fileAudio.mkdir();
					}
					Files.move(Paths.get(file.getPath()), Paths.get(fileAudio.getPath() + "\\" + file.getName()));
				}
				else
				{
					File fileOther = new File(link + "\\Other");
					if(!fileOther.exists()) {
						fileOther.mkdir();
					}
					Files.move(Paths.get(file.getPath()), Paths.get(fileOther.getPath() + "\\" + file.getName()));
				}
			}
		}
		System.out.println("\nĐã dọn dẹp xong!\n");
	}
	public static void deleteFile() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String link = getLinkFolder();
		File list[] = new File(link).listFiles();
		String ans = "";
		System.out.println("DANH SÁCH CÁC FILE TRONG FOLDER HIỆN TẠI");
		for(int i = 0; i < list.length; ++i) {
			System.out.println(i+1 + ". " + list[i].getName());
		}
		System.out.println("\nMời bạn nhập thứ tự các file cần xóa, cách nhau bởi dấu phẩy. Nhập 0 để xóa toàn bộ: ");
		ans = sc.nextLine();
		while(true)
		{
			String split_arr[] = ans.split(",");
			for(int i = 0; i < split_arr.length; ++i) {
				int value = Integer.parseInt(split_arr[i]);
				if(value < 0 || value > list.length + 1)
				{
					System.err.println("Chỉ mục không hợp lệ!!! Vui lòng thử lại...\n");
					System.out.println("\nMời bạn nhập thứ tự các file cần xóa, cách nhau bởi dấu phẩy. Nhập 0 để xóa toàn bộ: ");
					ans = sc.nextLine();
				}
			}
			break;
		}
		
		if(ans.equals("0")) {
			for(int i = 0; i < list.length; ++i) {
				boolean result = list[i].delete();
				if(!result) {
					System.err.println("Đã có lỗi xảy ra! Vui lòng thử lại sau...");
				}
			}
			System.out.println("Đã xóa thành công " + list.length + " files!");
		}
		else {
			String pos[] = ans.split(",");
			int count = 0;
			for(int i = 0; i < pos.length; ++i) {
				int pos_val = Integer.parseInt(pos[i]);
				boolean result = list[pos_val-1].delete();
				if(!result) {
					System.err.println("Đã có lỗi xảy ra! Vui lòng thử lại sau...");
				}
				count++;
			}
			System.out.println("Đã xóa thành công " + count + " files!\n");
		}
	}
	public static void deleteByExtension() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String link = getLinkFolder();
		File list[] = new File(link).listFiles();
		String ans = "";
		System.out.println("DANH SÁCH CÁC FILE TRONG FOLDER HIỆN TẠI");
		for(int i = 0; i < list.length; ++i) {
			System.out.println(i+1 + ". " + list[i].getName());
		}
		System.out.println("Nhập các định dạng file muốn xóa, cách nhau bởi dấu phẩy.(VD: pdf,docx,img...)");
		ans = sc.nextLine();
		String file_type[] = ans.split(",");
		int max_type = file_type.length;
		int number_del[] = new int[max_type]; 
		for(int i = 0; i < list.length; ++i) {
			for(int j = 0; j < file_type.length; ++j) {
				if( getFileExtension(list[i]).equals(file_type[j]) ) {
					boolean result = list[i].delete();
					if(!result) {
						System.err.println("Đã xảy ra lỗi! Vui lòng thử lại...");
					}
					number_del[j]++;
				}
			}
		}
		String statistic = "";
		for(int j = 0; j < max_type; ++j) {
			statistic += " " + number_del[j] + " files " + file_type[j].toUpperCase() + ((j < max_type - 1) ? "," : ".");
		}
		System.out.println("\nĐã xóa thành công" + statistic + "\n\n");
	}
	public static void openAnyFile() throws IOException {
		//Check and Init
		if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
		Desktop desktop = Desktop.getDesktop();
		
		//Display Information
		Scanner sc = new Scanner(System.in);
		String link = getLinkFolder();
		File list[] = new File(link).listFiles();
		System.out.println("DANH SÁCH CÁC FILE TRONG FOLDER HIỆN TẠI");
		for(int i = 0; i < list.length; ++i) {
			System.out.println(i+1 + ". " + list[i].getName());
		}
		
		//Get and handle input
		String ans = "";
		System.out.println("Nhập vào thứ tự của các file cần mở, ngăn cách bởi dấu phẩy. (VD: 1,4,8....)");
		ans = sc.nextLine();
		String pos_str[] = ans.split(",");
		int num_files = pos_str.length;
		int pos_val[] = new int[num_files];
		for(int i = 0; i < num_files; ++i) {
			pos_val[i] = Integer.parseInt(pos_str[i]);
		}
		int count = 0;
		for(int i = 0; i < num_files; ++i) {
			desktop.open(list[pos_val[i] - 1]);
			count++;
		}
		System.out.println("Đã mở thành công " + count + " files:\n");
		for(int i = 0; i < num_files; ++i) {
			System.out.println(i+1 + ". " +  list[pos_val[i] - 1].getName());
		}
		System.out.println("\n\n");
	}
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int choose;
		do {
			System.out.println("Chào mừng bạn đến với tiện ích quản lý file!\n\n");
			System.out.println("-----------------------------------TIỆN ÍCH QUẢN LÝ FILE---------------------------------");
			System.out.println("+      			[1]. Tìm kiếm file trong thư mục                         	+");
			System.out.println("+      			[2]. Đổi tên file hàng loạt trong thư mục                	+");
			System.out.println("+       		[3]. Dọn dẹp thư mục                               		+");
			System.out.println("+       		[4]. Xóa file theo chỉ mục                             		+");
			System.out.println("+       		[5]. Xóa file theo định dạng                               	+");
			System.out.println("+       		[6]. Mở file bất kỳ                               		+");
			System.out.println("+       		[7]. Thoát                                			+");
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.print("Mời bạn chọn chức năng: ");
			choose = sc.nextInt();
			
			switch (choose) {
			case 1:{
				System.out.println("Bạn đang dùng chức năng: Tìm kiếm file trong thư mục");
				searchByFileName();
				break;
			}
			case 2:{
				System.out.println("Bạn đang dùng chức năng: Đổi tên file hàng loạt trong thư mục");
				renameAllFiles();
				break;
			}
			case 3:{
				System.out.println("Bạn đang dùng chức năng: Dọn dẹp thư mục");
				cleanFolder();
				break;
			}
			case 4:{
				System.out.println("Bạn đang dùng chức năng: Xóa file theo chỉ mục");
				deleteFile();
				break;
			}
			case 5:{
				System.out.println("Bạn đang dùng chức năng: Xóa file theo định dạng");
				deleteByExtension();
				break;
			}
			case 6:{
				System.out.println("Bạn đang dùng chức năng: Mở file bất kỳ");
				openAnyFile();
				break;
			}
			case 7:{
				System.out.println("\nCảm ơn bạn đã sử dụng!\n");
				System.exit(0);
			}
			default:
				System.err.println("\nKhông có chức năng bạn lựa chọn!");
			}
		}while(choose != 7);
	}

}
