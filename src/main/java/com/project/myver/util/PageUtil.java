package com.project.myver.util;
/*
 * 	페이지 이동 기능을 만들기 위해서 보조적으로 사용할 유틸리티 클래스
 */
public class PageUtil {
	// 꼭 필요한 정보
	private	int	nowPage;			// 보고싶은 페이지
	private	int	totalCount;			// 총 데이터 개수
	
	// 보조적으로 필요한 정보
	private	int	lineCount;			// 한페이지당 보여주고 싶은 게시물의 개수
	private	int	pageGroup;			// 페이지 이동 기능은 몇개까지 가능할지를 지정 - [1][2][3][4][5] 의 개수
	
	// 계산에 의해서 만들어야 하는 실제 페이지 이동 정보
	private	int	totalPage;			// 총 몇페이지짜리 게시판인지를 기억할 변수
	private	int	startPage;			// 페이지 이동 기능이 몇페이지 부터 만들지를 지정
	private	int	endPage;			// 페이지 이동 기능을 몇페이지까지 만들지를 지정
	
	private int startNo; 			// 한 페이지에 출력할 리스트 중 첫번째 no
	private int endNo; 				// 한 페이지에 출력할 리스트 중 마지막 no
	
	// 블로그
	private int no;					// 블로그 카테고리 번호 혹은 블로그 번호로 사용!
	private String column_name;		// no의 컬럼명(블로그 카테고리 번호 혹은 블로그 번호)
	private boolean is_owner;		// 주인 여부
	private boolean is_neighbor;	// 이웃 여부(주인이 아닌 경우, 로그인한 본인이 해당 블로그의 이웃인지)
	// 검색 시 필요한 파라미터
	private String searchWord; //
	private String searchType; //
	
	//리뷰컨트롤러에서 필요한 파라미터
	private String rid;
	//기업 리뷰모아보기에서 사용
	private String coname;
	//QuestionSQL에서 사용(마이페이지 리스트출력하기용)
	private String qid;
	private int qno; //(문의사항 댓글 출력용)
	//자유게시판
	private String ftype;
	private String fid;
	private int fno; //(자유게시판 댓글 출력용)
	
	/*
	public PageUtil(int nowPage, int totalCount) {
		this(nowPage, totalCount, 10, 5);
	}*/
	
	public PageUtil() {}
	
	public PageUtil(int nowPage, int totalCount, int lineCount) {
		this.nowPage 	= nowPage;
		this.totalCount = totalCount;
		this.lineCount 	= lineCount;
		this.pageGroup 	= 10;
		calcTotalPage();
		calcStartPage();
		calcEndPage();
		calcStartNo();
		calcEndNo();
	}
	
	public PageUtil(int nowPage, int totalCount, int lineCount, int no, String column_name, boolean is_owner, boolean is_neighbor) {
		this.nowPage 	= nowPage;
		this.totalCount = totalCount;
		this.lineCount 	= lineCount;
		this.pageGroup 	= 10;
		this.no = no;
		this.column_name = column_name;
		this.is_owner = is_owner;
		this.is_neighbor = is_neighbor;
		
		calcTotalPage();
		calcStartPage();
		calcEndPage();
		calcStartNo();
		calcEndNo();
	}
	
	public PageUtil(int totalCount, int lineCount, int no, String column_name, boolean is_owner, int nowNo) {
		this.totalCount = totalCount;
		this.lineCount 	= lineCount;
		this.pageGroup 	= 10;
		this.no = no;
		this.column_name = column_name;
		this.is_owner = is_owner;
		
		calcNowPage(nowNo);
		calcTotalPage();
		calcStartPage();
		calcEndPage();
		calcStartNo();
		calcEndNo();
	}
	
	/*
	public PageUtil(int nowPage, int totalCount, int lineCount, int pageGroup) {
		this.nowPage 	= nowPage;
		this.totalCount = totalCount;
		this.lineCount 	= lineCount;
		this.pageGroup 	= pageGroup;
		calcTotalPage();
		calcStartPage();
		calcEndPage();
		calcStartNo();
		calcEndNo();
	}
	*/
	
	//notice, question(user)검색 - totalCnt에 쓰이는 생성자
	public PageUtil(String searchWord, String searchType) {
		this.searchType=searchType;
		this.searchWord=searchWord;
	}

	//mypage review. getList메서드에 쓰이는 생성자
	public PageUtil(String searchWord) {
		this.searchWord=searchWord;
	}


	// 시작 글번호 계산 : {총 게시글 수 - (한 페이지 게시글 수*현재 페이지 번호)}+1 3-5+1 8-10+1
	private void calcStartNo() {
		//startNo = totalCount-(lineCount*nowPage)+1;
		// (nowPage-1)*lineCount
		startNo = (nowPage-1)*lineCount;
	}
	
	// 끝 글번호 계산 : 총 게시글수 - ((현재 페이지 -1) * 한 페이지 게시글수) 8 - 5 = 3
	private void calcEndNo() {
		//endNo = totalCount-((nowPage-1)*lineCount);
		// 시작 글번호 + 한 페이지 게시글 수
		endNo = startNo+lineCount;
	}

	// 총 페이지수 계산
	private	void calcTotalPage() {
		//	총 페이지 수는 전체 게시물 개수를 한페이지당 보여줄 개수로 나눈 결과이다.
		//	다만	마지막 페이지는 개수가 부족해도 한페이지가 더 필요하다.
		//		예 >		100			10		10		몫을 이용하면 된다.
		//				101			10		11		몫 + 1을 해주어야 한다.
		totalPage = (totalCount % lineCount == 0) ? (totalCount / lineCount) : (totalCount / lineCount + 1);
	}
	
	// 시작 페이지 계산
	private	void calcStartPage() {
		//	현재 페이지를 가운데 놓고 양쪽에 다음 페이지를 놓는 방식으로 만들고자 한다.
		//	[5][6][7][8][9]
		//	[7][8][9][10][11]
		startPage = nowPage - (pageGroup / 2);//나머지는 버림
		if(startPage <= 0) {
			startPage = 1;
		}
	}
	
	// 마지막 페이지 계산
	private	void calcEndPage() {
		//	계산 원리는 시작 페이지부터 한페이지당 보여줄 페이지 그룹 -1 만큼 더하면 된다.
		endPage = startPage + (pageGroup - 1);
		
		//	마지막 페이지보다 더 큰 페이지가 나올 수 있다.
		if(endPage > totalPage) {
			endPage = totalPage;
		}
	}

	// 현재 페이지 계산
	/* 전체 게시물 개수:18
	 * lineCount: 5
	 * 내가 볼 게시글: 13
	 * 0  5 10 15
	 * 1  6 11 16
	 * 2  7 12 17
	 * 3  8 13 18
	 * 4  9 14
	 * 
	 * 1  2  3  4
	 * 
	 *  9/5=1+1=2
	 * 14/5=2+1=3
	 * 15/5=3+1=4
	 * 16/5=3+1=4
	 * nowPage = nowNo/lineCount + 1;
	 * */
	private void calcNowPage(int nowNo) {
		this.nowPage = nowNo/lineCount + 1;
	}
	
	
	
	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public int getQno() {
		return qno;
	}

	public void setQno(int qno) {
		this.qno = qno;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getLineCount() {
		return lineCount;
	}
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	public int getPageGroup() {
		return pageGroup;
	}
	public void setPageGroup(int pageGroup) {
		this.pageGroup = pageGroup;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public boolean getIs_owner() {
		return is_owner;
	}
	public void setIs_owner(boolean is_owner) {
		this.is_owner = is_owner;
	}
	public boolean getIs_neighbor() {
		return is_neighbor;
	}
	public void setIs_neighbor(boolean is_neighbor) {
		this.is_neighbor = is_neighbor;
	}

	@Override
	public String toString() {
		return "PageUtil [nowPage=" + nowPage + ", totalCount=" + totalCount + ", lineCount=" + lineCount
				+ ", pageGroup=" + pageGroup + ", totalPage=" + totalPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", startNo=" + startNo + ", endNo=" + endNo + ", no=" + no + ", column_name=" + column_name
				+ ", is_owner=" + is_owner + ", searchWord=" + searchWord + ", searchType=" + searchType
				+ ", rid=" + rid + ", coname=" + coname + ", qid=" + qid + ", qno=" + qno + ", ftype=" + ftype
				+ ", fid=" + fid + ", fno=" + fno + "]";
	}
}






