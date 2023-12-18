package cn.edu.ncu.liuzehao.moviet

data class Movie(
    val imageId: Int,
    val movieTitle: String,
    val movieClasses: List<String>,
    val score: Double
)

class Data {

    companion object {
        /**
         * 获取动作电影相关资源列表
         *
         */
        fun getActionItems(): List<Movie> {
            return listOf(
                Movie(R.drawable.batmandarknight, "蝙蝠侠：黑暗骑士", listOf("科幻", "动作", "犯罪"), 9.6),
                Movie(R.drawable.blackadam, "黑亚当", listOf("科幻", "动作"), 7.2),
                Movie(R.drawable.chaonengluzhandui, "超能陆战队", listOf("动作", "科幻", "动画"), 8.9),
                Movie(R.drawable.gongfu, "功夫", listOf("动作", "喜剧", "魔幻"), 9.2),
                Movie(R.drawable.touhaowanjia, "头号玩家", listOf("动作", "科幻", "情怀"), 9.3)
            )
        }

        /**
         * 获取爱情电影相关资源列表
         *
         */
        fun getRomanticItems(): List<Movie> {
            return listOf(
                Movie(R.drawable.chaoshikongtongju, "超时空同居", listOf("爱情", "科幻"), 8.3),
                Movie(R.drawable.qiyueyuansheng, "七月与安生", listOf("爱情", "青春"), 7.2),
                Movie(R.drawable.taitannike, "泰坦尼克号", listOf("爱情", "剧情"), 9.2),
                Movie(R.drawable.baisheyuanqi, "白蛇: 缘起", listOf("爱情", "动画", "魔幻"), 8.7),
                Movie(R.drawable.tiantangdianyingyuan, "天堂电影院", listOf("剧情"), 8.6)
            )
        }

        /**
         * 获取喜剧电影相关资源列表
         *
         */
        fun getComedyItems(): List<Movie> {
            return listOf(
                Movie(R.drawable.feichirensheng, "飞驰人生", listOf("喜剧", "亲情"), 8.4),
                Movie(R.drawable.gongfu, "功夫", listOf("喜剧", "剧情"), 9.2),
                Movie(R.drawable.threeidiots, "三傻大闹宝莱坞", listOf("喜剧", "剧情"), 7.9),
                Movie(R.drawable.xialuotefannao, "夏洛特烦恼", listOf("喜剧", "亲情"), 8.9),
                Movie(R.drawable.fengkuangdongwucheng, "疯狂动物城", listOf("喜剧", "动画"), 8.6)
            )
        }

        /**
         * 获取科幻电影相关资源列表
         *
         */
        fun getScifictionItems(): List<Movie> {
            return listOf(
                Movie(R.drawable.xintiao, "信条", listOf("烧脑", "科幻"), 9.1),
                Movie(R.drawable.xingjichuanyue, "星际穿越", listOf("科幻", "亲情", "热血"), 9.4),
                Movie(R.drawable.thematrix, "黑客帝国", listOf("爱情", "剧情", "科幻"), 9.2),
                Movie(R.drawable.huixinglaidenayiye, "彗星来的那一夜", listOf("科幻", "烧脑"), 8.7),
                Movie(R.drawable.hudiexiaoying, "蝴蝶效应", listOf("科幻", "剧情", "惊悚"), 8.6),
                Movie(R.drawable.chaoti, "超体", listOf("科幻", "剧情"), 8.5)
            )
        }
    }
}