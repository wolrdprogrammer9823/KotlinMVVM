package com.wolfsea.kotlinmvvm
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wolfsea.kotlinmvvm.adapter.SimpleDataBindingAdapter
import com.wolfsea.kotlinmvvm.bean.ColumnData
import com.wolfsea.kotlinmvvm.bean.User
import com.wolfsea.kotlinmvvm.extendmethod.toast
import com.wolfsea.kotlinmvvm.layoutmanager.DefineLayoutManager
import com.wolfsea.kotlinmvvm.layoutmanager.DefineLayoutManager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

//        CoroutineScope(Job() + Dispatchers.Main).launch {
//
//            /*var data = 10
//            launch(Dispatchers.IO) {
//                delay(2000)
//                data = 25
//            }.join()
//            Log.d("layout", "data:${data}")*/
//
//            var a1: Int
//            withContext(Dispatchers.IO) {
//                delay(2000)
//                a1 = 10
//            }
//
//            /*var a1 = 5
//            async {
//                delay(2000)
//                a1 = 10
//            }.await()*/
//
//            Log.d("layout", "a1:${a1}")
//
//            //val value = getValue()
//            //Log.d("layout", "value:${value}")
//            Log.d("layout", "111111111111")
//        }


    }

    private fun initView() {

        val mAdapter = SimpleDataBindingAdapter(this)

        mAdapter.onViewClick = {
            toast(mAdapter.items[it].name)
        }

        mAdapter.btnClick = {
            toast(mAdapter.items[it].value)
        }

        mvvm_rv.apply {
            layoutManager = DefineLayoutManager()
            adapter = mAdapter
        }

        CoroutineScope(Job() + Dispatchers.Main).launch {

            val userList = mutableListOf<User>()
            withContext(Dispatchers.Default) {
                userList.add(
                    User(
                        "小红",
                        25,
                        mutableListOf(ColumnData("12.5", 11.0F, 12.0F)),
                        "item1"
                    )
                )

                userList.add(
                    User(
                        "小明",
                        35,
                        mutableListOf(ColumnData("2.5", 1.0F, 2.0F)),
                        "item2"))

                userList.add(
                    User(
                        "小假",
                        15,
                        mutableListOf(ColumnData("102.5", 111.0F, 120.0F)),
                        "item3"
                    )
                )

                userList.add(
                    User(
                        "小同",
                        28,
                        mutableListOf(ColumnData("82.5", 81.0F, 82.0F)),
                        "item4"
                    )
                )

                userList.add(
                    User(
                        "小国",
                        20,
                        mutableListOf(ColumnData("1.5", 27.0F, 118.0F)),
                        "item5"
                    )
                )

                userList.add(
                    User(
                        "小钱",
                        21,
                        mutableListOf(ColumnData("2.5", 107.0F, 8.0F)),
                        "item6"
                    )
                )

                userList.add(
                    User(
                        "小强",
                        22,
                        mutableListOf(ColumnData("1.8", 7.0F, 28.0F)),
                        "item7")
                )

                userList.add(
                    User(
                        "小和",
                        23,
                        mutableListOf(ColumnData("4.0", 12.0F, 23.0F)),
                        "item8"
                    )
                )

                userList.add(
                    User(
                        "小戴",
                        24,
                        mutableListOf(ColumnData("11.5", 45.0F, 38.0F)),
                        "item9"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

                userList.add(
                    User(
                        "小挺",
                        25,
                        mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),
                        "item10"
                    )
                )

            }

            //设置底部留白,方便item的滚动.
            //val condition = userList.size >= 7
            //mvvm_rv.setPadding(0, 0, 0, if (condition) resources.getDimension(R.dimen.dp_60).toInt() else 0)
            //mvvm_rv.clipToPadding = !condition

            mAdapter.items.addAll(userList)
        }


        /*mAdapter.items.apply {
            add(User("小红", 25, mutableListOf(ColumnData("12.5",11.0F,12.0F)),"item1"))
            add(User("小明", 35, mutableListOf(ColumnData("2.5", 1.0F, 2.0F)),"item2"))
            add(User("小假", 15, mutableListOf(ColumnData("102.5", 111.0F, 120.0F)),"item3"))
            add(User("小同", 28, mutableListOf(ColumnData("82.5", 81.0F, 82.0F)),"item4"))
            add(User("小国", 20, mutableListOf(ColumnData("1.5", 27.0F, 118.0F)),"item5"))
            add(User("小钱", 21, mutableListOf(ColumnData("2.5", 107.0F, 8.0F)),"item6"))
            add(User("小强", 22, mutableListOf(ColumnData("1.8", 7.0F, 28.0F)),"item7"))
            add(User("小和", 23, mutableListOf(ColumnData("4.0", 12.0F, 23.0F)),"item8"))
            add(User("小戴", 24, mutableListOf(ColumnData("11.5", 45.0F, 38.0F)),"item9"))
            add(User("小挺", 25, mutableListOf(ColumnData("10.1", 28.0F, 18.0F)),"item10"))
        }*/
    }

    private suspend fun getValue():Int = coroutineScope {

        val one = async {
            delay(1000)
            5
        }

        val two = async {
            delay(2000)
            6
        }

        one.await() + two.await()
    }
}