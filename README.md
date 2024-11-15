# haejo

채팅 검색
input로 텍스트를 받아옴 => db에서 title 값 받아와서 같은 것 있는지 확인하고 있으면 출력 없으면 없다는 알림을 함

채팅방 개설
chat/create 으로 이동하여 공개여부, 비밀번호, 제목, 태그, 정원을 선택


유저 리스트 chatMember 인덱스 불러와야함

유저 검색
chatMemeber 리포지토리와 연결하여 검색을 해서 같은 nickname가 있으면 해당 사용자를 출력 없으면 없다는 알림

chat 에서 채팅방 목록을 봄 => 채팅방 선택시 해당 채팅방 chat/room{?}으로 이동함

chat가 생성되면 chatmsg, chatmember, chatlog에 chat_idx를 생성해야됨
이 단계에서는 
createRoom 메소드를 분리해서 createRoom이 실행되면 chat_idx를 생성하는 메소드를 따로생성
createRoom{
~
createlog()}

createlog{
~
creatememberlist()}

creatememberlist {}



이 과정에서 유저 정보도 집어넣어야함

chat생성 => chatlog생성 (~님이 입장했습니다) => chatmember를 생성 (chat_idx에 ~이메일의 ~닉네임을 가지고 권한이~인 사용자가 들어온 시간)
채팅방을 생성할 때 생성한 유저의 정보를 받아와야함 이메일, 닉네임,


개 씨~~~발 chatdto chatRepository에 어떻게 박히게 했는지 까먹음