package com.windywolf.rusher.modu10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainGameFragment extends Fragment {

    private Button creator;
    private Button undo;
    private GameMapView gameMapView;
    private LinearLayout moduloList;
    private ModuloQuestion question;
    private TextView tvLevel;
    private ArrayList<SingleModuloView> moduloViews = new ArrayList<>();
    private int currentLevel = 0;
    private String[] gameCode = {
            "{\"level\":1,\"modu\":\"2\",\"map\":[\"101\",\"000\",\"000\"],\"pieces\":[\"X\",\"X\"]}",
            "{\"level\":2,\"modu\":\"2\",\"map\":[\"101\",\"011\",\"000\"],\"pieces\":[\"XX\",\"X,X\",\"X,X\"]}",
            "{\"level\":3,\"modu\":\"2\",\"map\":[\"100\",\"010\",\"011\"],\"pieces\":[\"XXX\",\"X\",\".X,XX\",\"X,X,X\"]}",
            "{\"level\":4,\"modu\":\"2\",\"map\":[\"111\",\"000\",\"010\"],\"pieces\":[\"XX,X.\",\"X,X\",\"X,X,X\",\"XX\",\"X,X\"]}",
            "{\"level\":5,\"modu\":\"2\",\"map\":[\"100\",\"100\",\"101\"],\"pieces\":[\"X,X\",\"XX,.X,.X\",\"XXX\",\"XX\",\"XXX\"]}",
            "{\"level\":6,\"modu\":\"2\",\"map\":[\"010\",\"011\",\"110\"],\"pieces\":[\"XXX\",\"XX,X.,X.\",\"XX\",\"XX\",\"XX,X.,X.\",\"XX.,.XX\"]}",
            "{\"level\":7,\"modu\":\"2\",\"map\":[\"001\",\"001\",\"000\"],\"pieces\":[\"X\",\"XX\",\".X,XX,.X\",\"X.,XX,.X\",\"XX,.X\",\"X,X\"]}",
            "{\"level\":8,\"modu\":\"2\",\"map\":[\"110\",\"100\",\"111\"],\"pieces\":[\".X,XX,X.\",\"XXX,..X,..X\",\"X.,XX,.X\",\"X\",\".X,XX\",\"X,X,X\"]}",
            "{\"level\":9,\"modu\":\"2\",\"map\":[\"001\",\"100\",\"011\"],\"pieces\":[\"XX,XX\",\"XXX\",\"XX,X.,XX\",\"X.,XX\",\"X,X\",\"XX\",\"X\"]}",
            "{\"level\":10,\"modu\":\"2\",\"map\":[\"110\",\"110\",\"101\"],\"pieces\":[\"XXX,..X,..X\",\"XX.,.XX\",\"XX\",\"X,X\",\".X,XX\",\"XX\",\"XX.,.XX\"]}",
            "{\"level\":11,\"modu\":\"2\",\"map\":[\"1111\",\"1001\",\"1100\"],\"pieces\":[\"X,X\",\"X,X\",\"XXX,XX.\",\".X.,XXX\",\"XX,X.,X.\",\"XXXX,.X..\",\".X,XX,.X\"]}",
            "{\"level\":12,\"modu\":\"2\",\"map\":[\"1101\",\"1011\",\"0101\",\"1111\"],\"pieces\":[\"..X,XXX\",\"X.,XX\",\"..X,.XX,XX.,.X.\",\"X...,X...,XXXX\",\"XX.,.X.,.XX,..X\",\"X,X\",\".X,XX\",\"..X,XXX\"]}",
            "{\"level\":13,\"modu\":\"2\",\"map\":[\"0011\",\"1011\",\"0101\",\"0001\",\"1001\"],\"pieces\":[\"X,X,X,X,X\",\"XX,XX,X.,X.,X.\",\"XX.,.X.,XXX,.X.\",\"XXX,X..\",\"X,X,X\",\"X.,XX,.X\",\"XX,.X\",\"XXXX,.X..\"]}",
            "{\"level\":14,\"modu\":\"2\",\"map\":[\"10100\",\"01000\",\"00111\",\"00110\"],\"pieces\":[\"X.,X.,XX,.X\",\"XX,.X\",\".X,XX\",\"X,X\",\"X..,XXX\",\"X..,XXX,XX.,X..\",\"...X.,XXXXX,...X.\",\".X,XX,X.\",\"XX,XX,X.\"]}",
            "{\"level\":15,\"modu\":\"2\",\"map\":[\"1010\",\"0011\",\"0101\",\"1101\",\"1110\"],\"pieces\":[\"XX,.X\",\".X.,.X.,XXX\",\"X.,XX,X.\",\".X..,XX..,.XXX,.XX.\",\".X.,XXX\",\".X..,XX..,XXXX,.X..\",\".X,.X,XX\",\"X..,X..,XXX,XX.,X..\",\"X.,X.,XX\",\"X.,XX,.X\"]}",
            "{\"level\":16,\"modu\":\"2\",\"map\":[\"111011\",\"001110\",\"001000\",\"110110\",\"110100\"],\"pieces\":[\"X.X.,XXX.,..XX,..X.\",\"XX.,.X.,XXX,X..\",\"X...,XXXX,.X..,.X..\",\"....XX,XXXXX.,...X..,...X..\",\"X..,X..,XXX\",\"...X.,XXXXX,.X...,.X...\",\"XXX,X..\",\"XXX.,..XX,...X,..XX,..X.\",\".X,XX\",\"XX,.X,.X\"]}",
            "{\"level\":17,\"modu\":\"2\",\"map\":[\"00111\",\"11000\",\"10100\",\"01100\"],\"pieces\":[\".X..,.XXX,XX..,X...\",\"X..,XXX\",\"X.,XX,X.\",\"..XX,.XX.,XX..,.X..\",\"X..,XXX\",\".X.,XXX\",\"..X.,XXX.,..XX\",\"XX,.X,XX\",\".XXX,XX.X\",\"XXX\",\".X.,XXX,..X\"]}"
    };
    private String[] levelName = {
            "level 1", "level 2", "level 3", "level 4", "level 5", "level 6", "level 7", "level 8", "level 9", "level 10",
            "level 11", "level 12", "level 13", "level 14", "level 15", "level 16", "level 17"
    };

    public static MainGameFragment newInstance() {
        return new MainGameFragment();
    }

    public MainGameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        creator = (Button) view.findViewById(R.id.create_btn);
        gameMapView = (GameMapView) view.findViewById(R.id.game_map);
        moduloList = (LinearLayout) view.findViewById(R.id.modulo_list);
        undo = (Button) view.findViewById(R.id.undo_btn);
        tvLevel = (TextView) view.findViewById(R.id.level_tv);
        creator.setOnClickListener(onClickListener);
        undo.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_btn:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(levelName, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            gameMapView.clearData();
                            moduloList.removeAllViews();
                            moduloViews.clear();
                            currentLevel = i;

                            String inputCode = gameCode[i];
                            question = new ModuloQuestion(inputCode);
                            tvLevel.setText(getResources().getString(R.string.level_text, question.getLevel()));
                            gameMapView.setMapCode(question.getMap());
                            gameMapView.setModu(question.getModu());
                            GameMap map = new GameMap(question.getMap());

                            final ArrayList<SingleModulo> modulos = new ArrayList<>();
                            for (String modulo : question.getPieces()) {
                                modulos.add(new SingleModulo(modulo));
                                SingleModuloView singleModuloView = new SingleModuloView(getActivity());
                                singleModuloView.setModuloCode(modulo);
                                singleModuloView.setGameMap(map);
                                ViewGroup.LayoutParams lp = singleModuloView.getLayoutParams();
                                if (lp == null) {
                                    lp = new ViewGroup.LayoutParams(Calculator.dp2px(100, getActivity()), Calculator.dp2px(100, getActivity()));
                                } else {
                                    lp.width = Calculator.dp2px(100, getActivity());
                                    lp.height = Calculator.dp2px(100, getActivity());
                                }
                                singleModuloView.setLayoutParams(lp);
                                moduloList.addView(singleModuloView);
                                moduloViews.add(singleModuloView);
                                View view = new View(getActivity());
                                view.setLayoutParams(new ViewGroup.LayoutParams(Calculator.dp2px(20, getActivity()), ViewGroup.LayoutParams.MATCH_PARENT));
                                moduloList.addView(view);
                            }
                            gameMapView.setModulos(modulos);
                            gameMapView.setChangeListener(new GameMapView.OnModuloChangeListener() {
                                @Override
                                public void put(GameMapView v, int currentIndex) {
                                    moduloViews.get(currentIndex).putAt(v.getResultCode()[currentIndex * 2], v.getResultCode()[currentIndex * 2 + 1]);
                                    if (currentIndex == moduloViews.size() - 1) {
                                        if (v.checkWin()) {
                                            new AlertDialog.Builder(getActivity()).setTitle("You Win!").setPositiveButton("YES!YES!YES!", null).create().show();
                                        } else {
                                            new AlertDialog.Builder(getActivity()).setTitle("Thinking harder!").setPositiveButton("NO!!!!!!!", null).create().show();
                                        }
                                    }
                                }

                                @Override
                                public void remove(GameMapView v, int currentIndex) {
                                    moduloViews.get(currentIndex).canelPut();
                                }
                            });
                        }
                    }).setNegativeButton("Cancel", null).create().show();
                    break;
                case R.id.undo_btn:
                    gameMapView.undo();
                    break;
//                case R.id.reset_btn:
//
//                    break;
                default:
                    break;
            }
        }
    };

    private DialogInterface.OnClickListener onItemClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };
}
