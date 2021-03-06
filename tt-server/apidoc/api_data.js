define({ "api": [
  {
    "type": "post",
    "url": "/addOneAffair",
    "title": "增加一件事务",
    "name": "addOneAffair",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "affairID",
            "description": "<p>事务ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>事务标题</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>事务内容</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "date",
            "description": "<p>事务日期</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "status",
            "description": "<p>事务状态</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "get",
    "url": "/deleteOneAffair?",
    "title": "删除一件事务",
    "name": "deleteOneAffair",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "affairID",
            "description": "<p>事务ID</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "get",
    "url": "/getAllAffairs?",
    "title": "请求所有事务",
    "name": "getAllAffairs",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "docs",
            "description": "<p>所有事务的JSONArray字符串形式</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "get",
    "url": "/getDoneAffairs?",
    "title": "请求所有已完成事务",
    "name": "getDoneAffairs",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "docs",
            "description": "<p>所有事务的JSONArray字符串形式</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "get",
    "url": "/getOneAffair?",
    "title": "请求一件事务",
    "name": "getOneAffair",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "affairID",
            "description": "<p>事务ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "doc",
            "description": "<p>该事务的JSONObject字符串形式</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "post",
    "url": "/updateOneAffair",
    "title": "更新一件事务",
    "name": "updateOneAffair",
    "group": "Affair",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "affairID",
            "description": "<p>事务ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>事务标题（可选）</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>事务内容（可选）</p>"
          },
          {
            "group": "Parameter",
            "type": "Boolean",
            "optional": false,
            "field": "status",
            "description": "<p>事务状态（可选）</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/affairCtrl.js",
    "groupTitle": "Affair"
  },
  {
    "type": "get",
    "url": "/getOneShareTimes?",
    "title": "请求分享次数",
    "name": "getOneShareTimes",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "res.shareTimes.toString",
            "description": "<p>() 该用户的分享次数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "get",
    "url": "/getOneSignDate?",
    "title": "请求签到日期",
    "name": "getOneSignDate",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "res.signDate",
            "description": "<p>该用户的最近签到日期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "get",
    "url": "/getOneSignTimes?",
    "title": "请求连续签到次数",
    "name": "getOneSignTimes",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "res.signTimes.toString",
            "description": "<p>() 该用户的连续签到次数</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "post",
    "url": "/initOneRecord",
    "title": "初始化一项记录",
    "name": "initOneRecord",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "post",
    "url": "/updateOneSignDate",
    "title": "更新签到日期",
    "name": "updateOneSignDate",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "signDate",
            "description": "<p>签到日期</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "post",
    "url": "/updateShareTimes",
    "title": "更新分享次数",
    "name": "updateShareTimes",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isInc",
            "description": "<p>是否分享次数增1</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "post",
    "url": "/updateSignTimes",
    "title": "更新签到次数",
    "name": "updateSignTimes",
    "group": "Record",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isInc",
            "description": "<p>是否签到次数增1</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/recordCtrl.js",
    "groupTitle": "Record"
  },
  {
    "type": "get",
    "url": "/getUsername?",
    "title": "请求用户名",
    "name": "getUsername",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "doc.username",
            "description": "<p>该用户的用户名</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/userCtrl.js",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/updateUsername",
    "title": "更新用户名",
    "name": "updateUsername",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/userCtrl.js",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userLogin",
    "title": "用户登录",
    "name": "userLogin",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwd",
            "description": "<p>用户密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isSuccess",
            "description": "<p>是否成功</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/userCtrl.js",
    "groupTitle": "User"
  },
  {
    "type": "post",
    "url": "/userRegister",
    "title": "用户注册",
    "name": "userRegister",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "username",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "<p>用户邮箱</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwd",
            "description": "<p>用户密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "isSuccess",
            "description": "<p>是否成功</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "controller/userCtrl.js",
    "groupTitle": "User"
  }
] });
