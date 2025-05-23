<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>HR Calendar</title>
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Flatpickr CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/min/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
    
    
    
    

    <!-- jQuery JS -->
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>
    
    <!-- Moment.js -->
    <script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js'></script>
    
    <!-- FullCalendar JS -->
    <script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/5.11.2/main.min.js'></script>
    
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
		.header-container {
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 10;
        background: white;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
    	}
        #calendar {
            max-width: 900px;
            margin: 0 auto;
            margin-top: 60px;
        }
        /* 기본 이벤트 스타일 */
        .event-title {
            color: black;
        }
        /* Work Time 이벤트에 적용할 CSS */
        .work-time-event {
            background-color: #f0f3ff;
            border-left: 5px solid #007BFF;
            padding: 5px;
        }
        .startTime, .endTime, .totalTime {
            margin-top: 10px;
        }
        
    </style>
</head>
<body>
    <div class="header-container">
        <%@ include file="../common/header.jsp" %>
    </div>
    <div id="calendar"></div>
    <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">근무 상세 시간</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="detailForm">
                        <div class="mb-3">
                            <label for="startTime" class="form-label">출근 시간</label>
                            <input type="text" class="form-control" id="modalStartTime">
                        </div>
                        <div class="mb-3">
                            <label for="endTime" class="form-label">퇴근 시간</label>
                            <input type="text" class="form-control" id="modalEndTime">
                        </div>
                        <div class="mb-3">
                            <label for="totalTime" class="form-label">합계 시간</label>
                            <input type="text" class="form-control" id="modalTotalTime" readonly>
                        </div>
                        <button type="button" class="btn btn-primary" id="saveChanges">변경 내용 저장</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
     <script>
        $(document).ready(function () {
            const calendarEl = document.getElementById('calendar');

            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: '합계'
                },
                customButtons: {
                    합계: {
                        text: '합계',
                        click: function() {
                            let totalHours = 0;
                            let totalMinutes = 0;
                            $('.totalTime').each(function() {
                                let text = $(this).text().trim();

                                // 전처리로 [합계] : 및 추가 공백 제거
                                text = text.replace("[합계] :", "").trim();

                                // 분 부분이 있는지 확인하기 위해 두 가지 경우를 처리
                                let timeText = text.match(/(\d+)\s*시간\s*(\d*)\s*분?/);
                                
                                if (timeText) {
                                    const hours = parseInt(timeText[1]);
                                    const minutes = parseInt(timeText[2] || "0"); // 분이 없는 경우 0으로 설정
                                    totalHours += hours;
                                    totalMinutes += minutes;
                                } else {
                                    console.warn("Format mismatch: ", text);
                                }
                            });
                            totalHours += Math.floor(totalMinutes / 60);
                            totalMinutes = totalMinutes % 60;

                            alert('[합계] 총 근무 시간: ' + totalHours + '시간 ' + totalMinutes + '분');
                        }
                    }
                },
                events: function (fetchInfo, successCallback, failureCallback) {
                    const year = fetchInfo.start.getFullYear();
                    $.ajax({
                        url: '/api/holidays',
                        type: 'GET',
                        data: { year: year },
                        success: function(response) {
                            successCallback(response);
                        },
                        error: function(xhr, status, error) {
                            console.error('Error fetching data:', error);
                            failureCallback(error);
                        }
                    });
                },
                datesSet: function () {
                    calendar.refetchEvents();
                },
                dateClick: function(info) {
                    const dateStr = moment(info.date).format('YYYY-MM-DD');
                    let startTime = moment(dateStr + ' 09:00:00');
                    let endTime = moment(dateStr + ' 18:00:00');

                    // 해당 날짜의 이벤트를 검색하여 출근/퇴근 시간을 업데이트
                    let events = calendar.getEvents();
                    for (let event of events) {
                        if (moment(event.start).isSame(info.date, 'day')) {
                            if (event.title === "Work Time") {
                                startTime = moment(event.start);
                                endTime = event.end ? moment(event.end) : moment(dateStr + ' 18:00:00');
                                break;
                            }
                        }
                    }

                    // startTime과 endTime을 형식에 맞게 포맷팅
                    document.getElementById('modalStartTime').value = startTime.format('YYYY-MM-DD HH:mm:ss');
                    document.getElementById('modalEndTime').value = endTime.format('YYYY-MM-DD HH:mm:ss');

                    const start = moment(startTime);
                    const end = moment(endTime);

                    const duration = moment.duration(end.diff(start));
                    const hours = Math.floor(duration.asHours());
                    const minutes = duration.minutes();
                    document.getElementById('modalTotalTime').value = hours + '시간 ' + minutes + '분';

                    const modal = new bootstrap.Modal(document.getElementById('detailModal'));
                    modal.show();

                    // Flatpickr 초기화 
                    flatpickr("#modalStartTime", {
                        enableTime: true,
                        dateFormat: "Y-m-d H:i:S",
                        time_24hr: true
                    });

                    flatpickr("#modalEndTime", {
                        enableTime: true,
                        dateFormat: "Y-m-d H:i:S",
                        time_24hr: true
                    });
                },
                eventContent: function(arg) {
                    if (!arg.event.allDay) {
                        let startDate = arg.event.start ? moment(arg.event.start).format('HH:mm:ss') : "";
                        let endDate = arg.event.end ? moment(arg.event.end).format('HH:mm:ss') : "";
                        if (startDate || endDate) {
                            let content = document.createElement('div');
                            let html = '<div class="event-title">' + arg.event.title + '</div>';
                            html += '<div class="startTime"> [출근] : ' + startDate + '</div>';
                            if (endDate) {
                                let start = moment(arg.event.start);
                                let end = moment(arg.event.end);
                                let duration = moment.duration(end.diff(start));
                                let hours = Math.floor(duration.asHours());
                                let minutes = duration.minutes();
                                html += '<div class="endTime"> [퇴근] : ' + endDate + '</div>';
                                html += '<div class="totalTime"> [합계] : ' + hours + '시간 ' + minutes + '분</div>';
                            }
                            content.innerHTML = html;
                            return { domNodes: [content] };
                        }
                    } else {
                        let content = document.createElement('div');
                        content.innerHTML = '<div class="event-title">' + arg.event.title + '</div>';
                        return { domNodes: [content] };
                    }
                },
                eventClassNames: function(arg) {
                    if (arg.event.title === "Work Time") {
                        return ['has-event'];
                    }
                },
                eventDidMount: function (info) {
                    if (info.event.title === "Work Time") {
                        info.el.classList.add('work-time-event');
                    }
                }
            });

            document.getElementById('saveChanges').addEventListener('click', function() {
                const startTime = document.getElementById('modalStartTime').value;
                const endTime = document.getElementById('modalEndTime').value;
                const data = {
                    startTime: startTime,
                    endTime: endTime,
                };

                $.ajax({
                    url: '/api/updateWorkTimes.ex',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function(response) {
                        alert('변경 내용이 저장되었습니다.');
                        const modalElement = document.getElementById('detailModal');
                        const modal = bootstrap.Modal.getInstance(modalElement);
                        modal.hide();
                    },
                    error: function(xhr, status, error) {
                        console.error('Update failed:', error);
                    }
                });
            });

            calendar.render();
        });
    </script>
</body>
</html>