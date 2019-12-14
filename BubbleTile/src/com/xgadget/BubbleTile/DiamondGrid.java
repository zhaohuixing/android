package com.xgadget.BubbleTile;

import java.lang.Math;
import android.util.FloatMath;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DiamondGrid extends CPuzzleGrid{
	
	

public DiamondGrid()
{
}

public enGridType GetGridType()
{
    return enGridType.PUZZLE_GRID_DIAMOND;
}

public void InitializeMatrixLayout()
{
    int nRow = m_nEdge*2-1;
    int k = 0;
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
                pb.m_nCurrentLocationIndex = k;
                m_Bubbles.add(pb);
                ++k;
            }
        }
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
                pb.m_nCurrentLocationIndex = k;
                m_Bubbles.add(pb);
                ++k;
            }
        }
    }
}

public void InitializeSnakeLayout()
{
    int nRow = m_nEdge*2-1;
    int k = 0;
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            if(i%2 == 0)
            {    
                for(int j = 0; j <= i; ++j)
                {
                    BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
                    pb.m_nCurrentLocationIndex = k;
                    m_Bubbles.add(pb);
                    ++k;
                }
            }
            else
            {
                int n = k+i+1;
                for(int j = 0; j <= i; ++j)
                {
                    BubbleObject pb = new BubbleObject(n-j, m_bIconTemplate);
                    pb.m_nCurrentLocationIndex = k;
                    m_Bubbles.add(pb);
                    ++k;
                }
            }
        }
        else
        {
            if(i%2 == 0)
            {    
                for(int j = 0; j < nRow-i; ++j)
                {
                    BubbleObject pb = new BubbleObject(k+1, m_bIconTemplate);
                    pb.m_nCurrentLocationIndex = k;
                    m_Bubbles.add(pb);
                    ++k;
                }
            }
            else
            {
                int n = k+nRow-i;
                for(int j = 0; j < nRow-i; ++j)
                {
                    BubbleObject pb = new BubbleObject(n-j, m_bIconTemplate);
                    pb.m_nCurrentLocationIndex = k;
                    m_Bubbles.add(pb);
                    ++k;
                }
            }
        }
    }
}

public void InitializeSpiralLayout()
{
    if(m_nEdge <= 0)
        return;
    
    int nSize = m_nEdge * m_nEdge;
    int[] label = new int[nSize];//(int*)malloc(sizeof(int)*nSize);
    int nDirection = 0;
    int nCount = 0;
    int nRound = 0;
    while (nCount < nSize)
    {
        for (int i = nRound; i < m_nEdge -1 - nRound; i++)
        {
            if (nDirection == 0)
            {
                label[nRound*m_nEdge + i] = ++nCount;
            }
            else if (nDirection == 1)
            {
                label[i*m_nEdge + m_nEdge-1-nRound ] = ++nCount;
            }
            else if (nDirection == 2)
            {
                label[(m_nEdge-1-nRound)*m_nEdge + (m_nEdge-1 - i)] = ++nCount;
            }
            else if (nDirection == 3)
            {
                label[(m_nEdge-1-i)*m_nEdge + nRound] = ++nCount;
            }
        }
        
        nDirection = ++nDirection%4;
        if (nDirection == 0)
        {
            nRound++;
            if (nRound == m_nEdge -1 - nRound)
            {
                label[nRound*m_nEdge + nRound] = ++nCount;
                break; // last element is the center.
            }
        }
    }
    
    int nRow = m_nEdge*2-1;
    nCount = 0;
    for (int i = 0; i < nRow; ++i) 
    {
        int nLen = i < m_nEdge ? (i+1) : (nRow - i);
        int x = i < m_nEdge ? 0 : i-m_nEdge+1;
        int y = i < m_nEdge ? i : m_nEdge -1;
        
        for (int j = 0; j < nLen; j++)
        {            
            int nLabel = label[y*m_nEdge+x];
            x++;
            y--;
            
            BubbleObject pb = new BubbleObject(nLabel, m_bIconTemplate);
            pb.m_nCurrentLocationIndex = nCount++;
            m_Bubbles.add(pb);
        }
    }
    
//    free(label);
    label = null;
}

public void CalculateBubbleSize()
{
    float fMaxSize = (float)(m_nEdge*SQURT_3);
    float gridSize = this.GetGridMaxSize(fMaxSize);
    m_fBubbleSize = gridSize/fMaxSize;
}

public void InitializeGridCells()
{
    float fInnerWidth = m_fBubbleSize*(m_nEdge-1);
    float fInnerHeight = (float)(fInnerWidth*SQURT_3);
    PointF ptCenter = this.GetGridCenter();
    float startX = ptCenter.x;
    float startY = ptCenter.y - fInnerHeight*0.5f;
    float deltaX = m_fBubbleSize*0.5f;
    float deltaY = (float)(deltaX*SQURT_3);
  
    int nRow = m_nEdge*2-1;

    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {    
                    int l = (int)(((float)i)/2.0);
                    x += (j-l)*deltaX*2.0;
                }
                else
                {
                    float m = i/2.0f;
                    x += (((float)j)-m)*m_fBubbleSize; 
                }
                GridCell pc = new GridCell(x, y);
                m_Cells.add(pc);
            }
        }
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {    
                    float l = (((float)(nRow-i-1))/2.0f);
                    x += (j-l)*deltaX*2.0;
                }
                else
                {
                    float m = (nRow-i-1)/2.0f;
                    x += (((float)j)-m)*m_fBubbleSize; 
                }
                GridCell pc = new GridCell(x, y);
                m_Cells.add(pc);
            }    
        }
    }
    
}

public int GetBubbleNumberAtRow(int nRowIndex)
{
    int nRet = -1;

    int nRow = m_nEdge*2-1;
    
    if(0 <= nRowIndex && nRowIndex < m_nEdge)
        nRet = nRowIndex+1;
    else if(nRowIndex < nRow)
        nRet = nRow - nRowIndex;
    
    return nRet;
}

public int GetFirstIndexAtRow(int nRowIndex)
{
    int nRet = -1;
    
    int nRow = m_nEdge*2-1;
    
    if(0 <= nRowIndex && nRowIndex < m_nEdge)
        nRet = (nRowIndex+1)*nRowIndex/2;
    else if(nRowIndex < nRow)
    {
        int rEdge = nRow - nRowIndex;
        int rCount = rEdge*(rEdge+1)/2;
        nRet = CPuzzleGrid.GetDiamonGridBubbleNumber(m_nEdge)-rCount;
    }    
    
    return nRet;
}

public int GetLastIndexAtRow(int nRowIndex)
{
    int nRet = -1;
    
    int nRow = m_nEdge*2-1;
    
    if(0 <= nRowIndex && nRowIndex < nRow-1)
        nRet = this.GetFirstIndexAtRow(nRowIndex+1)-1;
    else if(nRowIndex == nRow-1)
    {
        nRet = CPuzzleGrid.GetDiamonGridBubbleNumber(m_nEdge)-1;
    }    
    
    return nRet;
}

public int GetGridRow(int nIndex)
{
    int nRet = -1;
    int nCount  = CPuzzleGrid.GetDiamonGridBubbleNumber(m_nEdge);
    int nRow = m_nEdge*2-1;
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            int j = i+1;
            int v1 = (i+1)*i/2;
            int v2 = (j+1)*j/2;
            if(v1 <= nIndex && nIndex < v2)
            {
                nRet = i;
                break;
            }
        }
        else
        {
            int j = nRow-i;
            int k = nRow-(i+1);
            int v1 = nCount - (j+1)*j/2;
            int v2 = nCount - (k+1)*k/2;
            if(v1 <= nIndex && nIndex < v2)
            {
                nRet = i;
                break;
            }
        }
    }
    
    return nRet;
}

public int GetGridColumne(int nIndex)
{
    int nRet = -1;
    
    int nRowIndex = this.GetGridRow(nIndex);
    int nRow = m_nEdge*2-1;
    int nFirstIndexAtRow = this.GetFirstIndexAtRow(nRowIndex);
    if(0 <= nRowIndex && nRowIndex <= m_nEdge-1)
    {    
        nRet = nIndex - nFirstIndexAtRow;
    }
    else if(nRowIndex < nRow)
    {
        int nStep = nIndex-nFirstIndexAtRow;
        int nTemp = nRowIndex-(m_nEdge-1);
        nRet = nStep+nTemp;
    }
    
    return nRet;
}



public void UpdateGridLayout()
{
    super.UpdateGridLayout();

    this.CalculateBubbleSize();
    
    float fInnerWidth = m_fBubbleSize*(m_nEdge-1);
    float fInnerHeight = (float)(fInnerWidth*SQURT_3);
    PointF ptCenter = this.GetGridCenter();
    float startX = ptCenter.x;
    float startY = ptCenter.y - fInnerHeight*0.5f;
    float deltaX = m_fBubbleSize*0.5f;
    float deltaY = (float)(deltaX*SQURT_3);
    
    int nIndex = 0;

    int nRow = m_nEdge*2-1;
    
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {    
                    int l = (int)(((float)i)/2.0);
                    x += (j-l)*deltaX*2.0;
                }
                else
                {
                    float m = i/2.0f;
                    x += (((float)j)-m)*m_fBubbleSize; 
                }
                m_Cells.get(nIndex).SetCenter(x, y);
                ++nIndex;
            }
        }
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {
                    float l = (((float)(nRow-i-1))/2.0f);
                    x += (j-l)*deltaX*2.0;
                }
                else
                {
                    float m = (nRow-i-1)/2.0f;
                    x += (((float)j)-m)*m_fBubbleSize; 
                }
                m_Cells.get(nIndex).SetCenter(x, y);
                ++nIndex;
            }    
        }
    }
}

public void CalculateCurrentTouchGesture()
{
    if(0 <= m_nTouchedCellIndex)
    {
        PointF ptCenter = this.GetBubbleCenter(m_nTouchedCellIndex);
        float x1 = ptCenter.x;
        float y1 = ptCenter.y;
        float x2 = m_ptTouchPoint.x;
        float y2 = m_ptTouchPoint.y;
        float dx = x2 - x1;
        float dx2 = dx*dx;
        float dy = y2 - y1;
        float dy2 = dy*dy;
        
        if(dy2 == 0 && dx2 == 0)
            return;
        
        Boolean bChange = false;
        if(m_Motion == enBubbleMotion.BUBBLE_MOTION_NONE)
        {    
            float temp = FloatMath.sqrt(dy2+dx2);
            if(temp < CGameLayout.GetBubbleMotionThreshold(m_fBubbleSize))
                return;
            
            if(dy2 <= dx2)
            {
                m_Motion = enBubbleMotion.BUBBLE_MOTION_HORIZONTAL;
                if(0 < dx)
                    m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
                else
                    m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
                m_fOffset = FloatMath.sqrt(dx2);
            }
            else
            {
                float f60sign = dx*dy;
                if(f60sign <= 0.0)
                {
                    m_Motion = enBubbleMotion.BUBBLE_MOTION_60DIAGONAL;
                    if(dy < 0)
                        m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
                    else
                        m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
                }
                else
                {
                    m_Motion = enBubbleMotion.BUBBLE_MOTION_120DIAGONAL;
                    if(dy < 0 && dx < 0)
                        m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
                    else
                        m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
                }
                m_fOffset = temp;
            }
            bChange = true;
        }
        else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_HORIZONTAL)
        {
            if(0 < dx)
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_FORWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
            }    
            else
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_BACKWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
            }    
            m_fOffset = FloatMath.sqrt(dx2);
        }
        else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_60DIAGONAL)
        {
            if(dy < 0)
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_FORWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
            }    
            else
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_BACKWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
            }    
            m_fOffset = FloatMath.sqrt(dy2+dx2);
        }
        else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_120DIAGONAL)
        {
            if(dy < 0 && dx < 0)
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_FORWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_FORWARD;
            }    
            else
            {    
                if(m_Direction != enMotionDirection.MOTION_DIRECTION_BACKWARD)
                    bChange = true;
                m_Direction = enMotionDirection.MOTION_DIRECTION_BACKWARD;
            }    
            m_fOffset = FloatMath.sqrt(dy2+dx2);
        }
        if(bChange)    
            super.InitializeArrowAnimation();
        
        if(m_fBubbleSize*CGameLayout.GetTouchSensitivity() <= m_fOffset)
        {
            this.ShiftBubbles();
            this.CleanTouchState();
            m_fOffset = m_fBubbleSize*CGameLayout.GetTouchSensitivity(); 
        }
    }
}

private void RowShiftForward(int nRowIndex)
{
    int nCount = this.GetBubbleNumberAtRow(nRowIndex);
    
    if(nCount <= 1)
        return;
    
    int nStartIndex = this.GetFirstIndexAtRow(nRowIndex);
    
    int b1 = this.GetBubbleDestinationIndex(nStartIndex + nCount - 1);
    BubbleObject pbFirst = m_Bubbles.get(b1);
    
    for(int i = (nStartIndex + nCount - 2); nStartIndex <= i; --i)
    {    
        b1 = this.GetBubbleDestinationIndex(i);
        BubbleObject pb = m_Bubbles.get(b1);
        pb.m_nCurrentLocationIndex = i+1;
    }
    pbFirst.m_nCurrentLocationIndex = nStartIndex;
}

private void HorzShiftBubblesForward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
    this.RowShiftForward(nRowIndex);
}

private void RowShiftBackward(int nRowIndex)
{
    int nCount = this.GetBubbleNumberAtRow(nRowIndex);
    
    if(nCount <= 1)
        return;
    
    int nStartIndex = this.GetFirstIndexAtRow(nRowIndex);
    
    int b1 = this.GetBubbleDestinationIndex(nStartIndex);
    BubbleObject pbFirst = m_Bubbles.get(b1);
    
    for(int i = nStartIndex + 1; i <= nStartIndex+nCount-1; ++i)
    {    
        b1 = this.GetBubbleDestinationIndex(i);
        BubbleObject pb = m_Bubbles.get(b1);
        pb.m_nCurrentLocationIndex = i-1;
    }
    pbFirst.m_nCurrentLocationIndex = nStartIndex+nCount-1;
}

private void HorzShiftBubblesBackward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
    this.RowShiftBackward(nRowIndex);
}

private void HorzShiftBubbles()
{
    switch(m_Direction)
    {
        case MOTION_DIRECTION_FORWARD:
            this.HorzShiftBubblesForward();
            break;
        case MOTION_DIRECTION_BACKWARD:
            this.HorzShiftBubblesBackward();
            break;
    }
}

public int GetFirstIndexAtDiagonal60Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        nRet = CPuzzleGrid.GetTriangleGridLastIndexAtRow(nColIndex);
    }
    
    return nRet;
}

public int GetLastIndexAtDiagonal60Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        nRet = this.GetFirstIndexAtRow(m_nEdge-1+nColIndex);
    }
    
    return nRet;
}

private int GetIndexAtDiagonal60Column(int nColIndex, int nCellIndex)
{
    int nRet = -1;
    
    int nRow = m_nEdge*2-1;
    if(0 <= nColIndex && nColIndex < m_nEdge && 0 <= nCellIndex && nCellIndex < m_nEdge)
    {
        int nRowIndex = nColIndex + nCellIndex;
        int nFistIndex = this.GetFirstIndexAtRow(nRowIndex);
        if(0 <= nRowIndex && nRowIndex <= m_nEdge-1)
        {
            nRet = nFistIndex + nColIndex;
        }
        else if(nRowIndex < nRow)
        {
            int nNewCellIndex = m_nEdge - (nCellIndex+1);
            nRet = nFistIndex + nNewCellIndex;
        }
    }
    
    return nRet;
}

private int GetBubbleNumberAtDiagonal60Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        nRet = m_nEdge;
    }
    
    return nRet;
}

private void Diagonal60ShiftForward(int nColIndex)
{
    int nCount = this.GetBubbleNumberAtDiagonal60Column(nColIndex);
    
    if(nCount <= 1)
        return;
    
    for(int i = 0; i < nCount-1; ++i)
    {   
        int index1 = this.GetIndexAtDiagonal60Column(nColIndex, i);
        int index2 = this.GetIndexAtDiagonal60Column(nColIndex, i+1);
        
        int b1 = this.GetBubbleDestinationIndex(index1);
        int b2 = this.GetBubbleDestinationIndex(index2);
        BubbleObject pb1 = m_Bubbles.get(b1);
        pb1.m_nCurrentLocationIndex = index2;
        BubbleObject pb2 = m_Bubbles.get(b2);
        pb2.m_nCurrentLocationIndex = index1;
    }
}

private void Diagonal60ShiftBubblesForward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    
    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);
    this.Diagonal60ShiftForward(nColIndex);
}

private void Diagonal60ShiftBackward(int nColIndex)
{
    int nCount = this.GetBubbleNumberAtDiagonal60Column(nColIndex);
    
    if(nCount <= 1)
        return;
    
    for(int i = nCount-1; 0 < i; --i)
    {   
        int index1 = this.GetIndexAtDiagonal60Column(nColIndex, i);
        int index2 = this.GetIndexAtDiagonal60Column(nColIndex, i-1);
        
        int b1 = this.GetBubbleDestinationIndex(index1);
        int b2 = this.GetBubbleDestinationIndex(index2);
        BubbleObject pb1 = m_Bubbles.get(b1);
        pb1.m_nCurrentLocationIndex = index2;
        BubbleObject pb2 = m_Bubbles.get(b2);
        pb2.m_nCurrentLocationIndex = index1;
    }
}

private void Diagonal60ShiftBubblesBackward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    
    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);
    this.Diagonal60ShiftBackward(nColIndex);
}

private void Diagonal60ShiftBubbles()
{
    switch(m_Direction)
    {
        case MOTION_DIRECTION_FORWARD:
            this.Diagonal60ShiftBubblesForward();
            break;
        case MOTION_DIRECTION_BACKWARD:
            this.Diagonal60ShiftBubblesBackward();
            break;
    }
}

private int GetGridDiagonal120Columne(int nIndex)
{
    int nRet = -1;
    
    int nRowIndex = this.GetGridRow(nIndex);
    int nRow = m_nEdge*2-1;
    int nFirstIndexAtRow = this.GetFirstIndexAtRow(nRowIndex);
    if(0 <= nRowIndex && nRowIndex <= m_nEdge-1)
    {    
        nRet = nRowIndex - (nIndex - nFirstIndexAtRow);
    }
    else if(nRowIndex < nRow)
    {
        int nStep = nIndex-nFirstIndexAtRow;
        nRet = (m_nEdge-1) - nStep;
    }
    
    return nRet;
}

public int GetFirstIndexAtDiagonal120Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        nRet = this.GetFirstIndexAtRow(nColIndex);
    }
    
    return nRet;
}

public int GetLastIndexAtDiagonal120Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        int nRowIndex = (m_nEdge-1)+nColIndex;
        int nFirstIndex = this.GetFirstIndexAtRow(nRowIndex);
        int nCount = this.GetBubbleNumberAtRow(nRowIndex);
        nRet = nFirstIndex + nCount - 1;
    }
    
    return nRet;
}

private int GetIndexAtDiagonal120Column(int nColIndex, int nCellIndex)
{
    int nRet = -1;
    
    int nRow = m_nEdge*2-1;
    if(0 <= nColIndex && nColIndex < m_nEdge && 0 <= nCellIndex && nCellIndex < m_nEdge)
    {
        int nRowIndex = nColIndex + nCellIndex;
        int nFistIndex = this.GetFirstIndexAtRow(nRowIndex);
        if(0 <= nRowIndex && nRowIndex <= m_nEdge-1)
        {
            nRet = nFistIndex + nCellIndex;
        }
        else if(nRowIndex < nRow)
        {
            int nCount = this.GetBubbleNumberAtRow(nRowIndex);
            int nNewCellIndex = (nCount-1) - (m_nEdge - (nCellIndex+1));
            nRet = nFistIndex + nNewCellIndex;
        }
    }
    
    return nRet;
}

private int GetBubbleNumberAtDiagonal120Column(int nColIndex)
{
    int nRet = -1;
    
    if(0 <= nColIndex && nColIndex < m_nEdge)
    {
        nRet = m_nEdge;
    }
    
    return nRet;
}

private void Diagonal120ShiftForward(int nColIndex)
{
    int nCount = this.GetBubbleNumberAtDiagonal120Column(nColIndex);
    
    if(nCount <= 1)
        return;
    
    for(int i = 0; i < nCount-1; ++i)
    {   
        int index1 = this.GetIndexAtDiagonal120Column(nColIndex, i);
        int index2 = this.GetIndexAtDiagonal120Column(nColIndex, i+1);
        
        int b1 = this.GetBubbleDestinationIndex(index1);
        int b2 = this.GetBubbleDestinationIndex(index2);
        BubbleObject pb1 = m_Bubbles.get(b1);
        pb1.m_nCurrentLocationIndex = index2;
        BubbleObject pb2 = m_Bubbles.get(b2);
        pb2.m_nCurrentLocationIndex = index1;
    }
}

private void Diagonal120ShiftBubblesForward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    
    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
    int nColIndex = nRowIndex - this.GetGridColumne(m_nTouchedCellIndex);

    this.Diagonal120ShiftForward(nColIndex);
}

private void Diagonal120ShiftBackward(int nColIndex)
{
    int nCount = this.GetBubbleNumberAtDiagonal120Column(nColIndex);
    
    if(nCount <= 1)
        return;
    
    for(int i = nCount-1; 0 < i; --i)
    {   
        int index1 = this.GetIndexAtDiagonal120Column(nColIndex, i);
        int index2 = this.GetIndexAtDiagonal120Column(nColIndex, i-1);
        
        int b1 = this.GetBubbleDestinationIndex(index1);
        int b2 = this.GetBubbleDestinationIndex(index2);
        BubbleObject pb1 = m_Bubbles.get(b1);
        pb1.m_nCurrentLocationIndex = index2;
        BubbleObject pb2 = m_Bubbles.get(b2);
        pb2.m_nCurrentLocationIndex = index1;
    }
}

private void Diagonal120ShiftBubblesBackward()
{
    if(m_nTouchedCellIndex < 0)
        return;
    
    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
    int nColIndex = nRowIndex - this.GetGridColumne(m_nTouchedCellIndex);

    this.Diagonal120ShiftBackward(nColIndex);
}

private void Diagonal120ShiftBubbles()
{
    switch(m_Direction)
    {
        case MOTION_DIRECTION_FORWARD:
            this.Diagonal120ShiftBubblesForward();
            break;
        case MOTION_DIRECTION_BACKWARD:
            this.Diagonal120ShiftBubblesBackward();
            break;
    }
}

public void ShiftBubbles()
{
    switch(m_Motion)
    {
        case BUBBLE_MOTION_HORIZONTAL:
            this.HorzShiftBubbles();
            break;
        case BUBBLE_MOTION_60DIAGONAL:
            this.Diagonal60ShiftBubbles();
            break;
        case BUBBLE_MOTION_120DIAGONAL:
            this.Diagonal120ShiftBubbles();
            break;
    }
    super.ShiftBubbles();
    
}

public void ExceuteUndoCommand(enBubbleMotion enMotion, enMotionDirection enDir, int nIndex)
{
    switch(enMotion)
    {
        case BUBBLE_MOTION_HORIZONTAL:
        {
            if(enDir == enMotionDirection.MOTION_DIRECTION_FORWARD)
            {
                this.RowShiftBackward(nIndex);
            }
            else if(enDir == enMotionDirection.MOTION_DIRECTION_BACKWARD)
            {
                this.RowShiftForward(nIndex);
            }
            break;
        }    
        case BUBBLE_MOTION_60DIAGONAL:
        {    
            if(enDir == enMotionDirection.MOTION_DIRECTION_FORWARD)
            {
                this.Diagonal60ShiftBackward(nIndex);
            }
            else if(enDir == enMotionDirection.MOTION_DIRECTION_BACKWARD)
            {
                this.Diagonal60ShiftForward(nIndex);
            }
            break;
        }    
        case BUBBLE_MOTION_120DIAGONAL:
        {    
            if(enDir == enMotionDirection.MOTION_DIRECTION_FORWARD)
            {
                this.Diagonal120ShiftBackward(nIndex);
            }
            else if(enDir == enMotionDirection.MOTION_DIRECTION_BACKWARD)
            {
                this.Diagonal120ShiftForward(nIndex);
            }
            break;
        }    
    }
}

private void DrawMotionGridHorizontal(Canvas context)
{
    if(m_nTouchedCellIndex < 0)
    {
        this.DrawStaticGrid(context);
        return;
    }
    
    int nRowIndex = this.GetGridRow(m_nTouchedCellIndex);
    float xOffset = m_fOffset;
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_BACKWARD)
    {
        xOffset *= -1.0f;
    }
    
    int k = 0;
    RectF rect;
    PointF ptCenter;
    float fRad = m_fBubbleSize/2.0f;
    
    int nRow = m_nEdge*2-1;
    
    float fyOffset = (float)((1.0f - SQURT_3*0.5f)*Math.abs(xOffset)*SHIFT_FACTOR0);
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                if(nRowIndex == i)
                {
                    ptCenter.x += xOffset;
                }
                else if(i < nRowIndex)
                {
                    ptCenter.y -= fyOffset;
                }
                else
                {
                    ptCenter.y += fyOffset;
                }
               rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
            
                ++k;
            }
        }
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                if(nRowIndex == i)
                {
                    ptCenter.x += xOffset;
                }
                else if(i < nRowIndex)
                {
                    ptCenter.y -= fyOffset;
                }
                else
                {
                    ptCenter.y += fyOffset;
                }
               
                rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
                ++k;
            }
        }
    }    
}

private void DrawMotionGrid60Diagonal(Canvas context)
{
    if(m_nTouchedCellIndex < 0)
    {
        this.DrawStaticGrid(context);
        return;
    }    
    
    int nColIndex = this.GetGridColumne(m_nTouchedCellIndex);
    
    float xOffset = m_fOffset*0.5f;
    float yOffset = (float)(xOffset*SQURT_3);
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        yOffset *= -1.0f;
    }
    else
    {
        xOffset *= -1.0f;
    }
    
    int k = 0;
    RectF rect;
    PointF ptCenter;
    float fRad = m_fBubbleSize/2.0f;

    int nRow = m_nEdge*2-1;
    float fxOffset = (float)((1.0f - SQURT_3*0.5f)*Math.abs(xOffset));
    for (int i = 0; i < nRow; ++i) 
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                if(nColIndex == j)
                {
                    ptCenter.x += xOffset;
                    ptCenter.y += yOffset;
                }
                else if(j < nColIndex)
                {
                    ptCenter.x -= fxOffset*SHIFT_FACTOR1;
                }
                else
                {
                    ptCenter.x += fxOffset*SHIFT_FACTOR2;
                }
                rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
                ++k;
            }
        }   
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                int nTempIndex = i-(m_nEdge-1)+j;
                if(nColIndex == nTempIndex)
                {
                    ptCenter.x += xOffset;
                    ptCenter.y += yOffset;
                }
                else if(nTempIndex < nColIndex)
                {
                    ptCenter.x -= fxOffset*SHIFT_FACTOR1;
                }
                else
                {
                    ptCenter.x += fxOffset*SHIFT_FACTOR2;
                }
                rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
                ++k;
            }
        }    
        
    }
}

public void DrawMotionGrid120Diagonal(Canvas context)
{
    if(m_nTouchedCellIndex < 0)
    {
        this.DrawStaticGrid(context);
        return;
    }    
    
    int nColIndex = this.GetGridDiagonal120Columne(m_nTouchedCellIndex);
    
    float xOffset = m_fOffset*0.5f;
    float yOffset = (float)(xOffset*SQURT_3);
    if(m_Direction == enMotionDirection.MOTION_DIRECTION_FORWARD)
    {
        xOffset *= -1.0;
        yOffset *= -1.0;
    }
    
    int k = 0;
    RectF rect;
    PointF ptCenter;
    float fRad = m_fBubbleSize/2.0f;
    
    int nRow = m_nEdge*2-1;
    float fxOffset = (float)((1.0f - SQURT_3*0.5f)*Math.abs(xOffset));
    for (int i = 0; i < nRow; ++i) 
    {
        if(i <= m_nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                if(nColIndex == (i-j))
                {
                    ptCenter.x += xOffset;
                    ptCenter.y += yOffset;
                }
                else if((i-j) < nColIndex)
                {
                    ptCenter.x += fxOffset*SHIFT_FACTOR2;
                }
                else
                {
                    ptCenter.x -= fxOffset*SHIFT_FACTOR1;
                }
                
                rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
                ++k;
            }
        }   
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                ptCenter = this.GetBubbleCenter(k);
                int nTempIndex = (m_nEdge-1)-j;
                if(nColIndex == nTempIndex)
                {
                    ptCenter.x += xOffset;
                    ptCenter.y += yOffset;
                }
                else if(nTempIndex < nColIndex)
                {
                    ptCenter.x += fxOffset*SHIFT_FACTOR2;
                }
                else
                {
                    ptCenter.x -= fxOffset*SHIFT_FACTOR1;
                }
                rect = new RectF(ptCenter.x-fRad, ptCenter.y-fRad, ptCenter.x-fRad + m_fBubbleSize, ptCenter.y-fRad + m_fBubbleSize);
                int nIndex = this.GetBubbleDestinationIndex(k);
                BubbleObject pb = m_Bubbles.get(nIndex);
                if(!pb.equals(null))
                    pb.DrawBubble(context, rect);
                ++k;
            }
        }    
        
    }
}

public void DrawMotionGrid(Canvas context)
{
    switch(m_Motion)
    {
        case BUBBLE_MOTION_HORIZONTAL:
            this.DrawMotionGridHorizontal(context);
            break;
        case BUBBLE_MOTION_60DIAGONAL:
            this.DrawMotionGrid60Diagonal(context);
            break;
        case BUBBLE_MOTION_120DIAGONAL:
            this.DrawMotionGrid120Diagonal(context);
            break;
    }
    if(m_Motion != enBubbleMotion.BUBBLE_MOTION_NONE)
        super.DrawArrowAnimation(context);
}

public int GetUndoLocationInfo()
{
    int nRet = -1;
  
    if(m_Motion == enBubbleMotion.BUBBLE_MOTION_HORIZONTAL)
    {
        nRet = this.GetGridRow(m_nTouchedCellIndex);
    }
    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_60DIAGONAL)
    {
        nRet = this.GetGridColumne(m_nTouchedCellIndex);
    }
    else if(m_Motion == enBubbleMotion.BUBBLE_MOTION_120DIAGONAL)
    {
        nRet = this.GetGridDiagonal120Columne(m_nTouchedCellIndex);
    }
    
    return nRet;
}

public void TestSuite()
{
    
}

public static void DrawSample(Canvas context, RectF rect, int nEdge)
{
    float fMaxSize = (float)(((float)(nEdge))*SQURT_3);
    
    float fBubbleSize = Math.min(rect.width(), rect.height())/fMaxSize;
    PointF ptCenter = new PointF(rect.left+rect.width()*0.5f, rect.top+rect.height()*0.5f);
    float fInnerWidth = fBubbleSize*(nEdge-1);
    float fInnerHeight = (float)(fInnerWidth*SQURT_3);
    float startX = ptCenter.x;
    float startY = ptCenter.y - fInnerHeight*0.5f;
    float deltaX = fBubbleSize*0.5f;
    float deltaY = (float)(deltaX*SQURT_3);
    float fRad = fBubbleSize*0.5f;
    
    Drawable  bkImage = null;
    enBubbleType enBType = GameConfiguration.GetBubbleType();
    
    if(enBType == enBubbleType.PUZZLE_BUBBLE_STAR)
    {
        bkImage = CResourceHelper.GetStarBubbleImage();//[ImageLoader LoadImageWithName:@"starballs.png"];
    }
    else if(enBType == enBubbleType.PUZZLE_BUBBLE_WOOD)
    {
        bkImage = CResourceHelper.GetFrogBubbleImage();//[ImageLoader LoadImageWithName:@"woodball.png"];
    }
    else
    {    
        bkImage = CResourceHelper.GetColorBubbleImage();//[ImageLoader LoadImageWithName:@"bubble.png"];
    }
    int nRow = nEdge*2-1;
    
    for(int i = 0; i < nRow; ++i)
    {
        if(i <= nEdge-1)
        {
            for(int j = 0; j <= i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {    
                    int l = (int)(((float)i)/2.0);
                    x += (j-l)*deltaX*2.0;
                }
                else
                {
                    float m = i/2.0f;
                    x += (((float)j)-m)*fBubbleSize; 
                }
                Rect rt = new Rect((int)(x-fRad), (int)(y-fRad), (int)(x-fRad+fBubbleSize), (int)(y-fRad+fBubbleSize));
                bkImage.setBounds(rt);
                bkImage.draw(context);
//                CGContextDrawImage(context, rt, bkImage);
            }
        }
        else
        {
            for(int j = 0; j < nRow-i; ++j)
            {
                float y = startY + deltaY*i;
                float x = startX;
                int k = i%2;
                if(k == 0)
                {    
                    float l = (((float)(nRow-i-1))/2.0f);
                    x += (j-l)*deltaX*2.0f;
                }
                else
                {
                    float m = (nRow-i-1)/2.0f;
                    x += (((float)j)-m)*fBubbleSize; 
                }
                Rect rt = new Rect((int)(x-fRad), (int)(y-fRad), (int)(x-fRad+fBubbleSize), (int)(y-fRad+fBubbleSize));
                bkImage.setBounds(rt);
                bkImage.draw(context);
//                CGContextDrawImage(context, rt, bkImage);
            }    
        }
    }
 //   CGImageRelease(bkImage);
}
}
