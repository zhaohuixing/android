package com.xgadget.SimpleGambleWheel;

import android.content.Context;
import android.util.AttributeSet;
import com.xgadget.widget.*;
import com.xgadget.widget.adapters.*;


public class PlayerBetLuckyNumberPicker extends WheelView 
{
	private int 	m_CurrentLuckNumber;
	protected MyAbsoluteLayout			m_LayoutContainer;
	CPlayerMakeBetController    m_Controller;
	
	
	/**
	 * @return the m_LayoutContainer
	 */
	public MyAbsoluteLayout getM_LayoutContainer() 
	{
		return m_LayoutContainer;
	}

	/**
	 * @param m_LayoutContainer the m_LayoutContainer to set
	 */
	public void setM_LayoutContainer(MyAbsoluteLayout m_LayoutContainer) 
	{
		this.m_LayoutContainer = m_LayoutContainer;
	}
	
	public void AttachController(CPlayerMakeBetController  Controller)
	{
		m_Controller = Controller;
	}
	
	public void SetCurrentPickerState(int nNumber, int nSelectedNumber)
	{
		this.ReloadByConfigures(nNumber, nSelectedNumber);
	}
	
	private void Initialize(Context context)
	{
		m_Controller = null;
		m_LayoutContainer = null;
		m_CurrentLuckNumber = 8;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentLuckNumber));
		setVisibleItems(8);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedNumber = newValue +1;
				}
				/*if (!timeScrolled) 
				{
					timeChanged = true;
					picker.setCurrentHour(hours.getCurrentItem());
					picker.setCurrentMinute(mins.getCurrentItem());
					timeChanged = false;
				}*/
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
                //wheel.setCurrentItem(itemIndex, true);
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
				//timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) 
			{
				//timeScrolled = false;
				//timeChanged = true;
				//picker.setCurrentHour(hours.getCurrentItem());
				//picker.setCurrentMinute(mins.getCurrentItem());
				//timeChanged = false;
			}
		};
		addScrollingListener(scrollListener);
        
	}

	public PlayerBetLuckyNumberPicker(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerBetLuckyNumberPicker(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public PlayerBetLuckyNumberPicker(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		Initialize(context);
	}

	public void PostOnLayoutHandle()
	{
		int width = GameLayoutConstant.GetCurrentPlayerBetPickerWidth();
		int height = GameLayoutConstant.GetCurrentPlayerBetPickerHeight();
		//int nScreentWidth = GameLayoutConstant.GetCurrentScreenWidth();
		//int nScreentHeight = GameLayoutConstant.GetCurrentContentViewHeight();
		if(m_LayoutContainer != null)
			m_LayoutContainer.SetChildNewDemension(this, width/2, height, 0, 0);		
	}	
	
	public void ReloadByConfigures(int nNumber, int nSelectedNumber)
	{
		int nTheme = Configuration.getCurrentThemeType();
		if(nTheme == GameConstants.GAME_THEME_NUMBER)
		{
			m_CurrentLuckNumber = nNumber;
			setViewAdapter(new NumericWheelAdapter(this.getContext(), 1, m_CurrentLuckNumber));
			setVisibleItems(m_CurrentLuckNumber);
			this.setCurrentItem(nSelectedNumber-1);
			OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
			{
				public void onChanged(WheelView wheel, int oldValue, int newValue) 
				{
					if(m_Controller != null)
					{
						m_Controller.m_nSelectedNumber = newValue +1;
					}
				}
			};
			addChangingListener(wheelListener);
			
			OnWheelClickedListener click = new OnWheelClickedListener() 
			{
	            public void onItemClicked(WheelView wheel, int itemIndex) 
	            {
	            }
	        };
	        addClickingListener(click);
		
			OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
			{
				public void onScrollingStarted(WheelView wheel) 
				{
				}
				public void onScrollingFinished(WheelView wheel) 
				{
				}
			};
			addScrollingListener(scrollListener);
			
			return;
		}
		else if(nTheme == GameConstants.GAME_THEME_ANIMAL)
		{
			m_CurrentLuckNumber = nNumber;
			setViewAdapter(new GraphicThemeIconPicker(m_CurrentLuckNumber, GameConstants.GAME_THEME_ANIMAL));
			setVisibleItems(m_CurrentLuckNumber);
			this.setCurrentItem(nSelectedNumber-1);
			OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
			{
				public void onChanged(WheelView wheel, int oldValue, int newValue) 
				{
					if(m_Controller != null)
					{
						m_Controller.m_nSelectedNumber = newValue +1;
					}
				}
			};
			addChangingListener(wheelListener);
			
			OnWheelClickedListener click = new OnWheelClickedListener() 
			{
	            public void onItemClicked(WheelView wheel, int itemIndex) 
	            {
	            }
	        };
	        addClickingListener(click);
		
			OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
			{
				public void onScrollingStarted(WheelView wheel) 
				{
				}
				public void onScrollingFinished(WheelView wheel) 
				{
				}
			};
			addScrollingListener(scrollListener);
			
			return;
		}
		if(nTheme == GameConstants.GAME_THEME_FOOD)
		{
			m_CurrentLuckNumber = nNumber;
			setViewAdapter(new GraphicThemeIconPicker(m_CurrentLuckNumber, GameConstants.GAME_THEME_FOOD));
			setVisibleItems(m_CurrentLuckNumber);
			this.setCurrentItem(nSelectedNumber-1);
			OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
			{
				public void onChanged(WheelView wheel, int oldValue, int newValue) 
				{
					if(m_Controller != null)
					{
						m_Controller.m_nSelectedNumber = newValue +1;
					}
				}
			};
			addChangingListener(wheelListener);
			
			OnWheelClickedListener click = new OnWheelClickedListener() 
			{
	            public void onItemClicked(WheelView wheel, int itemIndex) 
	            {
	            }
	        };
	        addClickingListener(click);
		
			OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
			{
				public void onScrollingStarted(WheelView wheel) 
				{
				}
				public void onScrollingFinished(WheelView wheel) 
				{
				}
			};
			addScrollingListener(scrollListener);
			return;
		}
		if(nTheme == GameConstants.GAME_THEME_FLOWER)
		{
			m_CurrentLuckNumber = nNumber;
			setViewAdapter(new GraphicThemeIconPicker(m_CurrentLuckNumber, GameConstants.GAME_THEME_FLOWER));
			setVisibleItems(m_CurrentLuckNumber);
			this.setCurrentItem(nSelectedNumber-1);
			OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
			{
				public void onChanged(WheelView wheel, int oldValue, int newValue) 
				{
					if(m_Controller != null)
					{
						m_Controller.m_nSelectedNumber = newValue +1;
					}
				}
			};
			addChangingListener(wheelListener);
			
			OnWheelClickedListener click = new OnWheelClickedListener() 
			{
	            public void onItemClicked(WheelView wheel, int itemIndex) 
	            {
	            }
	        };
	        addClickingListener(click);
		
			OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
			{
				public void onScrollingStarted(WheelView wheel) 
				{
				}
				public void onScrollingFinished(WheelView wheel) 
				{
				}
			};
			addScrollingListener(scrollListener);
			return;
		}
	}

	public void LoadAnimalIcons(Context context)
	{
		m_CurrentLuckNumber = 8;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentLuckNumber));
		setVisibleItems(8);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedNumber = newValue +1;
				}
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
			}
			public void onScrollingFinished(WheelView wheel) 
			{
			}
		};
		addScrollingListener(scrollListener);
		
	}
	
	public void LoadFruitIcons(Context context)
	{
		m_CurrentLuckNumber = 8;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentLuckNumber));
		setVisibleItems(8);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedNumber = newValue +1;
				}
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
			}
			public void onScrollingFinished(WheelView wheel) 
			{
			}
		};
		addScrollingListener(scrollListener);
	}
	
	public void LoadFlowerIcons(Context context)
	{
		m_CurrentLuckNumber = 8;
		setViewAdapter(new NumericWheelAdapter(context, 1, m_CurrentLuckNumber));
		setVisibleItems(8);
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() 
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue) 
			{
				if(m_Controller != null)
				{
					m_Controller.m_nSelectedNumber = newValue +1;
				}
			}
		};
		addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() 
		{
            public void onItemClicked(WheelView wheel, int itemIndex) 
            {
            }
        };
        addClickingListener(click);
	
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
			}
			public void onScrollingFinished(WheelView wheel) 
			{
			}
		};
		addScrollingListener(scrollListener);
	}

}
