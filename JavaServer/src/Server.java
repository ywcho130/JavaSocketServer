import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		ServerSocket server_socket = null;
		// 입력 스트림을 읽기 위한 클래스 선언
		BufferedReader in = null;
		// 다양한 데이터 유형의 값을 텍스트 형식으로 출력하는 데 사용되는 클래스 선언
		PrintWriter out = null;
		try {
			server_socket = new ServerSocket(4444);
			System.out.println("서버가 열렸습니다.");
			// 서버 소켓에서 클라이언트의 연결 요청을 수락하고, 클라이언트와의 통신을 위한 소켓을 반환한다.
			socket = server_socket.accept();
			// BufferedReader는 입력 스트림을 읽기 위한 클래스로, socket.getInputStream()을 통해 소켓의 입력 스트림을 가져온다. 
			// 그리고 InputStreamReader를 통해 바이트 스트림을 문자 스트림으로 변환하고(소켓 통신에서는 바이트 코드를 전송하기 때문에),
			// 마지막으로 BufferedReader를 생성하여 효율적으로 데이터를 읽을 수 있게 한다.
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 // 출력스트림 생성
			out = new PrintWriter(socket.getOutputStream(), true);
			
			
			while(true) {
				// 변수 msg에 Client로부터 읽어들인 데이터를 저장한다.
				String msg = in.readLine(); 
				System.out.println("Client로부터 온 메세지 : " + msg);
				// msg가 "끝냅시다."면 반복문에서 나와 대화를 종료한다.
				if(msg.equals("끝냅시다.")) {
					System.out.println("대화를 종료합니다.");
					break;
				}
				System.out.println("답장 : ");
				String reply = sc.nextLine();
				out.println(reply);
				/*
				 * 일반적으로 PrintWriter는 출력한 데이터를 버퍼에 저장하고, 버퍼가 가득 차거나 flush() 메서드가 호출될 때에만 실제로
				 * 데이터를 출력 스트림으로 보낸다. 이렇게 하는 이유는 작은 단위로 데이터를 전송할 경우 네트워크나 파일 I/O의 성능 저하를 방지하기
				 * 위함이다.
				 */
				// 버퍼링으로 인해 기록되지 않은 데이터를 출력 스트림에 모두 출력
				out.flush();
				
					
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
				if (socket != null) socket.close();
				if (server_socket != null) server_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}