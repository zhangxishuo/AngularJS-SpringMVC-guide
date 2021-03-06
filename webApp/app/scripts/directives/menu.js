'use strict';

/**
 * @ngdoc directive
 * @name webAppApp.directive:menu
 * @description
 * # 菜单导航
 * panjie
 */
angular.module('webAppApp')
    .directive('menu', function ($state, routes, teacher, $location) {
        return {
            templateUrl: 'views/directive/menu.html',
            restrict: 'E',
            link: function postLink($scope) {
                var self = this;
                self.init = function () {
                    $scope.data = {
                        currentLoginTeacher: {
                            id: undefined,
                            name: '匿名'
                        }
                    };

                    teacher.registerObserverCallback(function(teacher) {
                        $scope.data.currentLoginTeacher = teacher;
                    });
                };

                /**
                 * 是否点亮菜单
                 * @param name
                 * @returns {boolean}
                 */
                self.isActive = function (state) {
                    // 获取当前路由
                    var currentState = $state.$current;

                    // 存在parent属性
                    if (currentState.parent) {
                        // 一直找到根路由
                        while (currentState.parent.parent !== null) {
                            currentState = currentState.parent;
                        }

                        // 判断根路由
                        if (currentState.name === state.name) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };

                // 注销
                self.logout = function() {
                    teacher.logout(function(){
                       $location.url('/login');
                    });
                };

                self.init();
                $scope.isActive = self.isActive;
                $scope.console = console;
                $scope.$state = $state;
                $scope.routes = routes;
                $scope.logout = self.logout;
            }
        };
    })
;
