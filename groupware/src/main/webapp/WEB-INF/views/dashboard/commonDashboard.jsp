<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Dashboard</title>
<style>
    body {
        font-family: Roboto, sans-serif;
        font-size: 14px;
        line-height: 1.57142857;
        color: #757575;
        font-weight: normal;
        user-select: none;
        -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
        box-sizing: border-box;
        margin-top: 80px;
    }
    .widget-content {
        padding: 20px !important;
        border-radius: 3px;
        background: #fff;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        min-height: 300px;
    }
    .counter {
        text-align: left;
    }
    .counter-lg {
        padding: 35px 20px;
    }
    .counter-label {
        font-size: 15px;
        margin-bottom: 20px;
    }
    .date {
        font-size: 15px;
    }
    .checkin, .checkout {
        margin: 10px 0;
    }
    .checkin .txt, .checkout .txt {
        font-weight: 700;
    }
    .timer-area {
        color: #757575;
        font-size: 50px;
    }
    .margin-top-15 {
        margin-top: 15px;
    }
    .btn {
        display: block;
        width: 100%;
        padding: 20px;
        border: none;
        border-radius: 3px;
        font-size: 25px;
        margin-top: 10px;
        cursor: pointer;
        position: relative;
        text-align: left;
    }
    .btn-primary {
        background-color: #007bff;
        color: #fff;
    }
    .btn-success {
        background-color: #28a745;
        color: #fff;
    }
    .btn-danger {
        background-color: #dc3545;
        color: #fff;
    }
    .btn:hover {
        opacity: 0.9;
    }
    .hidden {
        display: none;
    }
    .circle-wrapper {
        position: absolute;
        top: 50%;
        left: 10px;
        transform: translateY(-50%);
        display: flex;
        align-items: center;
    }
    .circle {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #fff;
        margin-right: 10px;
    }
    .icon {
        font-size: 25px;
    }
    .text-primary {
        color: #007bff;
    }
    .text-success {
        color: #28a745;
    }
    .text-danger {
        color: #dc3545;
    }
    .font-size-25 {
        font-size: 25px;
    }
    
    .font-size-15 {
        font-size: 15px;
    }
    
    .font-size-16 {
        font-size: 16px;
    }
    
    .font-size-17 {
        font-size: 17px;
    }
    .font-size-14 {
        font-size: 14px;
    } 
    .font-size-13 {
        font-size: 13px;
    } 
    .font-size-11 {
        font-size: 11px;
    }
    .checkin-container {
        text-align: right;
        padding-right: 80px;
    }
    .timerWrap {
        text-align: center;
        color: black;
    }
    .widget-container {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px; /* 박스 간격 */
    }
     .approval {
     	margin: 5px;
     	color: black;
     }
</style>
</head>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var now = new Date();
        var year = now.getFullYear();  // 연도
        var month = now.getMonth() + 1;  // 월 index가 0부터라서 +1 해줘야함
        if(month < 10) month = '0' + month;
        var date = now.getDate();  // 일
        if(date < 10) date = '0' + date;
        var day = getDay(now.getDay());  // 요일
        var fullDateStr = year + '.' + month + '.' + date + ' (' + day + ')'; //2025.03.07 (금)
        document.getElementById('todayDate').textContent = fullDateStr;

        const timerWrap = document.querySelector('.timerWrap');

        function updateTime() {
            const now = new Date();
            const formattedTime = now.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit', second: '2-digit' });
            timerWrap.textContent = formattedTime;
        }

        setInterval(updateTime, 1000);
        updateTime();

        const checkinBtn = document.querySelector('[data-action="checkin"]');
        const checkoutBtn = document.querySelector('[data-action="checkout"]');

        var startDt = '${startDt}';
        var endDt = '${endDt}';

        const checkinTxt = document.querySelector('.checkin .txt');
        const checkoutTxt = document.querySelector('.checkout .txt');
        const alterTimeBtn = document.querySelector('[data-action="alterTime"]');

        checkinTxt.textContent = startDt ? startDt : '출근전';
        checkoutTxt.textContent = endDt ? endDt : '퇴근전';

        function initializeButtons() {
            if (!startDt) {
                checkinBtn.classList.remove('hidden');
                checkoutBtn.classList.add('hidden');
                alterTimeBtn.classList.add('hidden');
            } else if (startDt && !endDt) {
                checkinBtn.classList.add('hidden');
                checkoutBtn.classList.remove('hidden');
                alterTimeBtn.classList.add('hidden');
            } else {
                checkinBtn.classList.add('hidden');
                checkoutBtn.classList.add('hidden');
                alterTimeBtn.classList.remove('hidden');
            }
        }

        initializeButtons();

        checkinBtn.addEventListener('click', function() {
            if (!startDt) {
                checkinBtn.classList.add('hidden');
                checkoutBtn.classList.remove('hidden');

                $.ajax({
                    url: '/startJob.ex',
                    type: 'POST',
                    data: {},
                    success: function(response) {
                      location.reload();
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                    }
                });

                checkinTxt.textContent = '출근함';
                checkoutTxt.textContent = '퇴근전';
            }
        });

        checkoutBtn.addEventListener('click', function() {
            if (!endDt) {
                console.log("1");
                checkinBtn.classList.remove('hidden');
                checkoutBtn.classList.add('hidden');
                checkinTxt.textContent = '출근전';
                checkoutTxt.textContent = '퇴근함';
            }

            $.ajax({
                url: '/endJob.ex',
                type: 'POST',
                data: {},
                success: function(response) {
                  location.reload();
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                }
            });
        });

        alterTimeBtn.addEventListener('click', function() {
            window.location.href = '/hrList.ex';
        });
        
        

        const approveBtn = document.querySelector('[data-action="approve"]');
        
        approveBtn.addEventListener('click', function() {

            window.location.href = '/document.ex';

        });
    });

    function getDay(day) {
        var dayStr;
        switch (day) {
            case 0:
                dayStr = '일';
                break;
            case 1:
                dayStr = '월';
                break;
            case 2:
                dayStr = '화';
                break;
            case 3:
                dayStr = '수';
                break;
            case 4:
                dayStr = '목';
                break;
            case 5:
                dayStr = '금';
                break;
            case 6:
                dayStr = '토';
                break;
        }
        return dayStr;
    }
</script>
<body>
    <div class="widget-container">
        <div class="widget-content padding-20 radius">
            <div class="widget-work counter text-left">
                <div>
                    <div>
                        <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                            <div class="counter-label font-size-15">
                                <span id="todayDate" class="date"></span>
                            </div>
                            <div class="checkin">
                                <span class="counter-number font-size-50">
                                    <span class="timer-area grey-700"><div class="timerWrap"></div></span>
                                </span>
                            </div>
                            <div class="checkin-container">
                                <div class="checkin">
                                    <span class="grey-500">출근 : </span>
                                    <span id="startJob" class="txt font-weight-700 grey-700">출근전</span>
                                </div>
                                <div class="checkout">
                                    <span class="grey-500">퇴근 : </span> 
                                    <span id="endJob" class="txt font-weight-700 grey-700">퇴근전</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="margin-top-15">
                    <button data-action="checkin" class="btn btn-sm btn-primary btn-block margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-primary">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">출근하기</div>
                        <span class="font-size-14">Have a Nice day</span>
                    </button>
                    <button data-action="checkout" class="btn btn-sm btn-success btn-block hidden margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-success">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">퇴근하기</div>
                        <span class="font-size-14">Have a Nice day</span>
                    </button>

                    <button data-action="alterTime" class="btn btn-sm btn-success btn-block hidden margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-success">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">수정하기</div>
                        <span class="font-size-14">Have a Nice day</span>
                    </button>
                </div>
            </div>
        </div>
        <!-- 새로운 전자결재 박스 -->
        <div class="widget-content padding-20 radius">
            <div class="widget-work counter text-left">
                <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                    <div class="counter-label font-size-15">
                    	<span class="font-size-15">전자결재</span>
                    </div>
                    <div class="approval-section">
                        <div class="approval">
                            <span class="grey-500">결재하기 : </span>
                            <span id="approvalDoc" class="txt font-weight-700 grey-700">건</span>
                        </div>
                    </div>
					<div class="approval-section">
                        <div class="approval">
                            <span class="grey-500">결재완료 : </span>
                            <span id="approvedDoc" class="txt font-weight-700 grey-700">건</span>
                        </div>
                     </div>
                    <div class="approval-section">
                        <div class="approval">
                            <span class="grey-500">결재진행 : </span>
                            <span id="proceedDoc" class="txt font-weight-700 grey-700">건</span>
                        </div>
					</div>
					<div class="approval-section">
                        <div class="approval">
                            <span class="grey-500">결재반려 : </span>
                            <span id="returendDoc" class="txt font-weight-700 grey-700">건</span>
                        </div>
                     </div>
                     <div class="approval-section">
                        <div class="approval">
                            <span class="grey-500">결재취소 : </span>
                            <span id="cancelDoc" class="txt font-weight-700 grey-700">건</span>
                        </div>
                     </div>
                    </div>
                </div>
                
                <div class="fixed-bottom margin-top-15">
                    <button id="commonBtn"  data-action="approve" class="btn btn-sm btn-primary btn-block margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-primary">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">결재하러가기</div>
                        <span class="font-size-14">승인/반려</span>
                    </button>
                </div>
            </div>
        <!-- 세 번째 추가 박스 -->
        
        <div class="widget-content padding-20 radius">
            <div class="widget-work counter text-left">
                <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                    <div class="counter-label font-size-15">
                        Korea IT
                    </div>
                    <div class="other-section">
                    <div class="other">
                        <span class="grey-500">실시간 뉴스</span>
                    </div>
                    <div class="news-section">
                        <ul class="list-unstyled">
                            <c:forEach var="article" items="${articles}">
                                <li>
                                    <a href="${article.link}" target="_blank" class="news-link">
                                        ${article.title}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="widget-content padding-20 radius">
			<div class="widget-work counter text-left">
                <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                    <div class="counter-label font-size-15">
                        팀박스
                    </div>
                    <div class="other-section">
                    <div class="news-section">
                        <ul class="list-unstyled">
                            <c:forEach var="teams" items="${teams}">
                                <li>
                                    <a href="${teams.link}" target="_blank" class="news-link">
                                        ${teams.title}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    </div>
                </div>
            </div>
        </div>
        
        </div>
        <!-- 
        <div class="widget-content padding-20 radius">
            <div class="widget-work counter text-left">
                <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                    <div class="counter-label font-size-15">
                        기타
                    </div>
                    <div class="other-section">
                        <div class="other">
                            <span class="grey-500">기타 정보 : </span>
                            <span id="otherInfo" class="txt font-weight-700 grey-700">내용 없음</span>
                        </div>
                    </div>
                </div>
                <div class="margin-top-15">
                    <button data-action="actionOne" class="btn btn-sm btn-primary btn-block margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-primary">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">기타 작업</div>
                        <span class="font-size-14">기타 작업 수행</span>
                    </button>
                    <button data-action="actionTwo" class="btn btn-sm btn-success btn-block hidden margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-success">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">기타 작업 2</div>
                        <span class="font-size-14">기타 작업 2 수행</span>
                    </button>
                </div>
            </div>
        </div>
        
        <div class="widget-content padding-20 radius">
            <div class="widget-work counter text-left">
                <div class="counter counter-lg padding-vertical-35 padding-horizontal-20">                        
                    <div class="counter-label font-size-15">
                        기타
                    </div>
                    <div class="other-section">
                        <div class="other">
                            <span class="grey-500">기타 정보 : </span>
                            <span id="otherInfo" class="txt font-weight-700 grey-700">내용 없음</span>
                        </div>
                    </div>
                </div>
                <div class="margin-top-15">
                    <button data-action="actionOne" class="btn btn-sm btn-primary btn-block margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-primary">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">기타 작업</div>
                        <span class="font-size-14">기타 작업 수행</span>
                    </button>
                    <button data-action="actionTwo" class="btn btn-sm btn-success btn-block hidden margin-top-10 text-left has-action btn-rotate-horizontal">
                        <div class="circle-wrapper">
                            <div class="icon text-success">
                                <i class="fas fa-arrow-circle-right"></i>
                            </div>
                        </div>
                        <div class="font-size-25">기타 작업 2</div>
                        <span class="font-size-14">기타 작업 2 수행</span>
                    </button>
                </div>
            </div>
        </div>-->
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            function fetchDocumentCounts() {
                $.ajax({
                    url: '/dashboard/counts.ex',
                    method: 'GET',
                    success: function(response) {
                        $("#approvalDoc").text(response.approval + ' 건');
                        $("#approvedDoc").text(response.approved + ' 건');
                        $("#proceedDoc").text(response.proceed + ' 건');
                        $("#returendDoc").text(response.returned + ' 건');
                        $("#cancelDoc").text(response.cancel + ' 건');
                    },
                    error: function(error) {
                        console.error("Error fetching document counts:", error);
                    }
                });
            }

            // 페이지 로드 시 문서 카운트 가져오기
            fetchDocumentCounts();
        });
    </script>
</body>
</html>