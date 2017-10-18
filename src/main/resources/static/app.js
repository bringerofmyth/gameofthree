var stompClient = null;
var playerId = -1;
var joinedGame = -1;
var status = '';
var player = '';
var lastNumber = -1;
var started = false;
var isAuto = false;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#game_messages").html("");
}

function connect(game_id) {
    console.log('connect with game_id ' + game_id);
    var socket = new SockJS('localhost:8080/messagepoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/move/' + joinedGame, function (message) {
            console.log('subscribe message ' + message);
            showMessage(JSON.parse(message.body));
        });
    });
}


function showMessage(message) {
    var p1 = player == 'player1';
    if (message.errorMessage) {
        $("#game_messages").prepend("<tr><td>" + message.gameMessage + "</td></tr>");
        return;
    }

    if (message.status) {
        status = message.status;

        if (message.resultNumber) {
            lastNumber = message.resultNumber;
        }

        if (!started && p1 && status == "P1_MOVE") {
            $('#game_log').show();
            $('#move_list_view').show();
            $('#status_text').text('Game Started.');
            $('#create_view').hide();
            started = true;
        }
        var yourTurn = p1 && status == "P1_MOVE" || !p1 && status == "P2_MOVE";
        if (yourTurn) {
            if (!isAuto) {
                $('#move').prop("disabled", false);
            }
            else {
                autoMove();
            }
        }
        if (status == "P1_WINS" || status == 'P2_WINS') {
            $('#move').prop("disabled", true);
            $('#auto_mode').prop("disabled", true);
            var string = "";
            if (p1) {
                string = status == "P1_WINS" ? "You have won!" : "You have lost."
            }
            else {
                string = status == "P2_WINS" ? "You have won!" : "You have lost."
            }
            $('#status_text').text(string);
        }
    }
    else {
        $('#status_text').text('An error occurred');
        return;
    }

    console.log('status: ' + status);
    $("#game_messages").prepend("<tr><td>" + message.gameMessage + "</td></tr>");
}
function createPlayer() {
    console.log($("#username").val());
    var json = JSON.stringify({'username': $("#username").val()});
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/game/player',
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (data, textStatus, jqXHR) {
            console.log("success " + data.id + " , text status :" + textStatus);
            playerId = Number(data.id);
            $("#game_join").show();
            $("#create_view").show();
            $("#create_player_view").hide();
            $('#status_text').text('Good. Now create a game or join a game.');

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("An Error Occured.");
            console.log("failed " + errorThrown + " jq : " + jqXHR + " textstatus " + textStatus);
        },
        dataType: 'json'
    });
}

function createGame() {
    var json = JSON.stringify({'name': $("#game_name").val(), 'createdBy': playerId});
    console.log(json);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/game/create',
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (data, textStatus, jqXHR) {
            console.log("success " + data.id + " , text status :" + textStatus);
            joinedGame = Number(data.id);
            connect(joinedGame);
            player = 'player1';
            $("#game_join").hide();
            $("#create").prop('disabled', true);
            $('#player_text').text('Player 1');
            $('#status_text').text('Game created. You are Player 1. Waiting for Player 2 to join...');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#status_text').text('Game cannot be created.');
            console.log("failed " + errorThrown + " jq : " + jqXHR + " textstatus " + textStatus);
        },
        dataType: 'json'
    });

}
function listGames() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/game/list',
        contentType: "application/json; charset=utf-8",
        success: function (data, textStatus, jqXHR) {
            var options = [];
            if (data.length < 1) {
                $("#join").prop("disabled", true);
                $('#status_text').text('There is no available game. Please create one.');
                return;
            }
            for (i = 0; i < data.length; i++) {
                options.push("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
            }
            $('#game_list').find('option').remove().end();
            $('#game_list').append(options.join(""));
            $("#join").prop("disabled", false);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("failed " + errorThrown + " jq : " + jqXHR + " textstatus " + textStatus);
        },
        dataType: 'json'
    });

}

function joinGame() {
    console.log($("#game_list").val() + ' selected');
    var gameId = Number($("#game_list").val());
    var json = JSON.stringify({'gameId': gameId, 'playerId': playerId});
    console.log(json);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/game/join',
        contentType: "application/json; charset=utf-8",
        data: json,
        success: function (data, textStatus, jqXHR) {
            console.log("joined");
            joinedGame = gameId;
            connect(gameId);
            player = 'player2';
            $('#player_text').text('Player 2');
            $("#create_view").hide();
            $("#start_view").show();
            $('#status_text').text('Joined into game. You are Player 2. Please start the game.');

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('An error occurred');
            console.log("failed " + errorThrown + " jq : " + jqXHR + " textstatus " + textStatus);
        },
        dataType: 'json'
    });
}

function sendMessage(playerId, gameId, actionValue, isStart) {
    console.log("sending message");
    stompClient.send("/app/room/" + joinedGame, {}, JSON.stringify({
        'playerId': playerId,
        'gameId': gameId,
        'actionValue': actionValue,
        'isStart': isStart
    }));
}

function move() {
    console.log($("#move_list").val() + ' selected');
    var isP1 = player == 'player1';
    var actionNumber = Number($("#move_list").val());
    var sum = lastNumber + actionNumber;
    if (status == 'P1_WINS' || status == 'P2_WINS') {
        alert('Game Finished! ' + status);
        return;
    }
    else if (isP1 && status != 'P1_MOVE' || !isP1 && status != 'P2_MOVE') {
        alert('Not your turn!');
        $('#status_text').text('Not your turn!');
    }
    if (sum % 3 != 0) {
        alert('Wrong move! ' + sum + ' is not divisible by 3');
        return;
    }
    sendMessage(playerId, joinedGame, actionNumber, false);
    $("#move").prop("disabled", true);
}

function startGame() {
    sendMessage(playerId, joinedGame, 0, true);
    if (!started) {
        $("#create_view").hide();
        $("#game_join").hide();
        $('#game_log').show();
        $('#move_list_view').show();
        $("#start_view").hide();
        $('#status_text').text('Game Started.');
        started = true;
    }
    $("#move").prop("disabled", true);
}
function setAuto() {
    isAuto = $("#auto_mode").is(":checked");
    console.log('Auto play set to ' + isAuto);
    $('#move').prop("disabled", isAuto);
    if (isAuto) {
        autoMove();
    }
}
function autoMove() {
    var p1 = player == 'player1';
    var yourTurn = p1 && status == "P1_MOVE" || !p1 && status == "P2_MOVE";
    if (yourTurn) {
        var remainder = (lastNumber % 3);
        var numberToMove = 0;
        if (remainder == 1) {
            numberToMove = -1;
        }
        else if (remainder == 2) {
            numberToMove = 1;
        }
        sendMessage(playerId, joinedGame, numberToMove, false);
    }
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#register").click(function () {
        createPlayer();
    });
    $("#create").click(function () {
        createGame();
    });
    $("#list").click(function () {
        listGames();
    });
    $("#join").click(function () {
        joinGame();
    });
    $("#start").click(function () {
        startGame();
    });
    $("#move").click(function () {
        move();
    });
    $("#auto_mode").change(function () {
        setAuto();
    });
});

