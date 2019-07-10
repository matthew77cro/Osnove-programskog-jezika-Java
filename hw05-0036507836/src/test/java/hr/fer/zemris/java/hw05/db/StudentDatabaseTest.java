package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {
	
	static String[] data1;
	static String[] data2;
	static String[] data3;
	static StudentDatabase database1;
	static StudentDatabase database2;
	static StudentDatabase database3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data1 = ("0000000001	Akšamović	Marin	2\r\n" + 
				"0000000002	Bakamović	Petra	3\r\n" + 
				"0000000003	Bosnić	Andrea	4\r\n" + 
				"0000000004	Božić	Marin	5\r\n" + 
				"0000000005	Brezović	Jusufadis	2\r\n" + 
				"0000000006	Cvrlje	Ivan	3\r\n" + 
				"0000000007	Čima	Sanjin	4\r\n" + 
				"0000000008	Ćurić	Marko	5\r\n" + 
				"0000000009	Dean	Nataša	2\r\n" + 
				"0000000010	Dokleja	Luka	3\r\n" + 
				"0000000011	Dvorničić	Jura	4\r\n" + 
				"0000000012	Franković	Hrvoje	5\r\n" + 
				"0000000013	Gagić	Mateja	2\r\n" + 
				"0000000014	Gašić	Mirta	3\r\n" + 
				"0000000015	Glavinić Pecotić	Kristijan	4\r\n" + 
				"0000000016	Glumac	Milan	5\r\n" + 
				"0000000017	Grđan	Goran	2\r\n" + 
				"0000000018	Gužvinec	Matija	3\r\n" + 
				"0000000019	Gvardijan	Slaven	4\r\n" + 
				"0000000020	Hibner	Sonja	5\r\n" + 
				"0000000021	Jakobušić	Antonija	2\r\n" + 
				"0000000022	Jurina	Filip	3\r\n" + 
				"0000000023	Kalvarešin	Ana	4\r\n" + 
				"0000000024	Karlović	Đive	5\r\n" + 
				"0000000025	Katanić	Dino	2\r\n" + 
				"0000000026	Katunarić	Zoran	3\r\n" + 
				"0000000027	Komunjer	Luka	4\r\n" + 
				"0000000028	Kosanović	Nenad	5\r\n" + 
				"0000000029	Kos-Grabar	Ivo	2\r\n" + 
				"0000000030	Kovačević	Boris	3\r\n" + 
				"0000000031	Krušelj Posavec	Bojan	4\r\n" + 
				"0000000032	Lučev	Tomislav	5\r\n" + 
				"0000000033	Machiedo	Ivor	2\r\n" + 
				"0000000034	Majić	Diana	3\r\n" + 
				"0000000035	Marić	Ivan	4\r\n" + 
				"0000000036	Markić	Ante	5\r\n" + 
				"0000000037	Markoč	Domagoj	2\r\n" + 
				"0000000038	Markotić	Josip	3\r\n" + 
				"0000000039	Martinec	Jelena	4\r\n" + 
				"0000000040	Mišura	Zrinka	5\r\n" + 
				"0000000041	Orešković	Jakša	2\r\n" + 
				"0000000042	Palajić	Nikola	3\r\n" + 
				"0000000043	Perica	Krešimir	4\r\n" + 
				"0000000044	Pilat	Ivan	5\r\n" + 
				"0000000045	Rahle	Vedran	2\r\n" + 
				"0000000046	Rajtar	Robert	3\r\n" + 
				"0000000047	Rakipović	Ivan	4\r\n" + 
				"0000000048	Rezić	Bruno	5\r\n" + 
				"0000000049	Saratlija	Branimir	2\r\n" + 
				"0000000050	Sikirica	Alen	3\r\n" + 
				"0000000051	Skočir	Andro	4\r\n" + 
				"0000000052	Slijepčević	Josip	5\r\n" + 
				"0000000053	Srdarević	Dario	2\r\n" + 
				"0000000054	Šamija	Pavle	3\r\n" + 
				"0000000055	Šimunov	Ivan	4\r\n" + 
				"0000000056	Šimunović	Veljko	5\r\n" + 
				"0000000057	Širanović	Hrvoje	2\r\n" + 
				"0000000058	Šoić	Vice	3\r\n" + 
				"0000000059	Štruml	Marin	4\r\n" + 
				"0000000060	Vignjević	Irena	5\r\n" + 
				"0000000061	Vukojević	Renato	2\r\n" + 
				"0000000062	Zadro	Kristijan	3\r\n" + 
				"0000000063	Žabčić	Željko	4\r\n").split("\r\n");		
		data2 = ("0000000001	Akšamović	Marin	0\r\n").split("\r\n");
		data3 = ("0000000001	Akšamović	Marin	5\r\n" + "0000000001	Borna	Filip	5\r\n").split("\r\n");
	
		database1 = new StudentDatabase(data1);
	}
	
	@Test
	void testDuplicateJMBAGs() {
		assertThrows(IllegalArgumentException.class, () -> database2 = new StudentDatabase(data2));
	}
	
	@Test
	void testGradeNotInRange() {
		assertThrows(IllegalArgumentException.class, () -> database3 = new StudentDatabase(data3));
	}

	@Test
	void testForJMBAG() {
		StudentRecord student1 = database1.forJMBAG("0000000031");
		assertEquals("Bojan", student1.getFirstName());
		assertEquals("Krušelj Posavec", student1.getLastName());
		assertEquals("0000000031", student1.getJmbag());
		assertEquals(4, student1.getFinalGrade());
		
		StudentRecord student2 = database1.forJMBAG("0000000064");
		assertEquals(null, student2);
	}
	
	@Test
	void testFilterTrue() {
		List<StudentRecord> list = database1.filter((record) -> true);
		assertEquals(63, list.size());
	}
	

	@Test
	void testFilterFalse() {
		List<StudentRecord> list = database1.filter((record) -> false);
		assertEquals(0, list.size());
	}

}
