package com.example.movieSearch
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SharedPreferenceManager
import com.example.movieSearch.databinding.ActivityMainBinding
import com.example.searchModule.MovieSearchResponse
import com.example.searchModule.MovieSearchService
import com.example.searchModule.MovieSearchView
import java.util.*

const val STRING_NULL = ""
const val MAX_LOG_CNT = 10
const val NAVER_ID = "KvfkPaq5V52MCqyYYmUc"
const val NAVER_PW = "fnPfJB7Rwl"
const val START_PAGE = 1
const val LIST_INC = 10


class MainActivity: AppCompatActivity(), MovieSearchView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  movieSearchService: MovieSearchService
    private lateinit var movieRVAdapter: MovieRVAdapter
    private lateinit var searchingText: String
    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        검색 기록 queue
        val sharedPreferenceManager = SharedPreferenceManager(this)
        val searchLogs = sharedPreferenceManager.searchLogs

        init()

//        LogActivity와 통신, 받아온 검색어로 검색
        val searchWithLog: ActivityResultLauncher<Intent>  = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if(result.resultCode == RESULT_OK){
                searchingText = result.data?.getStringExtra(SEARCH_KEY).toString()
                updateSearchLog(searchLogs, sharedPreferenceManager)
                movieSearchService.movieSearch(NAVER_ID, NAVER_PW, searchingText, START_PAGE)
                searchViewModel.updatePage(START_PAGE)
                binding.mainSearchEt.setText(searchingText)
            }
        }
//        검색기록 확인
        binding.mainSearchLogBtn.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            searchWithLog.launch(intent)
        }

//        영화목록 최신화 후 페이징 최신화
        searchViewModel.movieList.observe(this){
            val movieList = searchViewModel.movieList.value
            movieRVAdapter.submitList(movieList)
            val page = searchViewModel.page.value!!
            searchViewModel.updatePage(page + LIST_INC)
        }

//        검색결과 마지막 영화 보이면 다음 영화 로드 후 페이징 최신화
        binding.mainMoviesRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleMoviePosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val lastMoviePosition = movieRVAdapter.currentList.size - 1
                val page = searchViewModel.page.value!!
                if(lastVisibleMoviePosition == lastMoviePosition){
                    Log.d("순서",page.toString())
                    movieSearchService.movieSearch(NAVER_ID, NAVER_PW, searchingText, page)
                }
            }
        })

//        영화 클릭시 브라우저 연결
        movieRVAdapter.setItemClickListener(object: MovieRVAdapter.MovieClickListener{
            override fun clickMovie(holder: MovieRVAdapter.ViewHolder, position: Int) {
                holder.binding.itemMovieCl.setOnClickListener {
                    val url = movieRVAdapter.currentList[position].link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        })

//        검색 클릭리스너
        binding.mainSearchBtn.setOnClickListener {
            searchingText = binding.mainSearchEt.text.toString()
            updateSearchLog(searchLogs, sharedPreferenceManager)
            movieSearchService.movieSearch(NAVER_ID, NAVER_PW, searchingText, START_PAGE)
            searchViewModel.updatePage(START_PAGE)
        }

    }

    private fun init() {
        //        뷰모델 생성
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        //        검색 api 서비스 세팅
        movieSearchService = MovieSearchService()
        movieSearchService.setMovieSearchView(this)

        //        영화 어댑터
        movieRVAdapter = MovieRVAdapter()
        binding.mainMoviesRv.adapter = movieRVAdapter
        binding.mainMoviesRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    private fun updateSearchLog(
        searchLogs: LinkedList<String>,
        sharedPreferenceManager: SharedPreferenceManager
    ) {
        //            검색 기록 중복 확인
        for (i in 0 until searchLogs.size) {
            if (searchLogs[i] == searchingText) {
                searchLogs.removeAt(i)
                break
            }
        }
        //            검색기록 밀어내기
        if (searchLogs.size == MAX_LOG_CNT) {
            searchLogs.pop()
        }
        //            검색기록 저장
        searchLogs.offer(searchingText)
        sharedPreferenceManager.saveSearchLog(searchLogs)
    }

    override fun onMovieSearchSuccess(result: MovieSearchResponse) {
//        첫 페이지 검색이면 새로 담아주고 아니면 기존 리스트에 추가해서 업데이트
        if(searchViewModel.page.value == START_PAGE){
            searchViewModel.updateMovieList(result.items!!)
            if(result.items!!.size==0){
                Toast.makeText(this, "검색 결과가 없습니다",Toast.LENGTH_SHORT).show()
            }
        }else{
            val movieList = searchViewModel.movieList.value!!
//          페이징이 넘어갔는데도 같은 목록이 불러오는 경우가 있음 그래서 비교가 필요함
            if(movieList == result.items!!){
                Toast.makeText(this, "검색 결과가 더 이상 없습니다",Toast.LENGTH_SHORT).show()
            }else{
                movieList.addAll(result.items!!)
            }

            searchViewModel.updateMovieList(movieList)
            if(result.items!!.size==0){
                Toast.makeText(this, "검색 결과가 더 이상 없습니다",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMovieSearchFailure() {
        Toast.makeText(this, "오류가 발생했습니다 다시 시도해주세요",Toast.LENGTH_SHORT).show()
    }

}