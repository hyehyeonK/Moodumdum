package org.androidtown.dumdumtest3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AngleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
    static List sRandomImages = new ArrayList<>();
    static {
        for(int i=0; i<100; i++)
            sRandomImages.add(new Random().nextInt() | 0xff000000);
    }
*/

    FrameLayout mRefreshLayout;
    StackLayout mStackLayout;
    Adapter mAdapter;

    List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadData(0);
    }

    private void initView() {
        mRefreshLayout = (FrameLayout) findViewById(R.id.layout);

        /*
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        int page = 0;
                        curPage = 0;
                        mData = new ArrayList<>( Arrays.asList(String.valueOf(page*3), String.valueOf(page*3+1), String.valueOf(page*3+2)));
                        mStackLayout.setAdapter(mAdapter = new Adapter(mData));
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        */

        mStackLayout = (StackLayout) findViewById(R.id.stack_layout);



        mStackLayout.setAdapter(mAdapter = new Adapter(mData = new ArrayList<>()));
        mStackLayout.addPageTransformer(
                new MyStackPageTransformer(),
                new MyAlphaTransformer(),       // 渐变
                new AngleTransformer()     // 角度
        );


        mStackLayout.setOnSwipeListener(new StackLayout.OnSwipeListener() {
            @Override
            public void onSwiped(View swipedView, int swipedItemPos, boolean isSwipeLeft, int itemLeft) {
                //toast((isSwipeLeft? "往左": "往右") + "移除" + mData.get(swipedItemPos) + "." + "剩余" + itemLeft + "项");

                // 少于5条, 加载更多
                if(itemLeft < 5){
                    loadData(++ curPage);
                }
            }
        });

    }
    int curPage = 0;

    private void loadData(final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.getData().addAll(Arrays.asList(String.valueOf(page*3), String.valueOf(page*3+1), String.valueOf(page*3+2)));
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    class Adapter extends StackLayout.Adapter<Adapter.ViewHolder>{
        List<String> mData;

        public void setData(List<String> data) {
            mData = data;
        }

        public List<String> getData() {
            return mData;
        }

        public Adapter(List<String> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            //holder.mTextView.setText(mData.get(position));

            // 배경 색
            //holder.itemView.findViewById(R.id.mainCard).setBackgroundColor(sRandomColors.get(position%sRandomColors.size()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends StackLayout.ViewHolder{
            TextView mTextView;
            TextView mTextView2;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.contents);
                mTextView2 = (TextView) itemView.findViewById(R.id.writer);
            }
        }


    }
    void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}